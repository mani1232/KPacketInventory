package cc.worldmandia.api.gui

import cc.worldmandia.api.gui.feature.SlotManaged
import cc.worldmandia.api.gui.feature.UserBasedGui
import cc.worldmandia.api.gui.feature.editable.EditableGui
import cc.worldmandia.api.gui.feature.editable.IEditableGui
import cc.worldmandia.api.gui.type.BaseType
import cc.worldmandia.api.gui.type.GuiType
import cc.worldmandia.api.integration.packet.GuiClickPacket
import cc.worldmandia.api.slot.BaseSlot
import com.github.retrooper.packetevents.protocol.item.ItemStack
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerCloseWindow
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems
import net.kyori.adventure.text.Component
import java.util.concurrent.ConcurrentHashMap

open class SyncGui(
    protected val title: Component,
    protected val guiContent: EditableGui, guiType: BaseType,
) : BaseGui(guiType), SlotManaged, UserBasedGui {

    override fun processSlots(packet: GuiClickPacket) {
        guiContent.guiItems[packet.clickPacket.slot]?.beforeClickEvent(packet)
    }

    protected val playersForSync: MutableSet<User> = ConcurrentHashMap.newKeySet()

    @GuiDslMarker
    class SyncGuiBuilder {
        var title: Component = Component.empty()
        var guiType: BaseType = GuiType.GENERIC_9X6
        private var guiContent: EditableGui = EditableGui(ConcurrentHashMap())

        fun content(contentBuilder: EditableGuiBuilder.() -> Unit) {
            guiContent = EditableGuiBuilder().apply(contentBuilder).build()
        }

        fun build(): SyncGui = SyncGui(title, guiContent, guiType)
    }

    @GuiDslMarker
    class EditableGuiBuilder() : IEditableGui {
        private var guiItems = ConcurrentHashMap<Int, BaseSlot>()
        var carriedItem: BaseSlot? = null

        fun build(): EditableGui = EditableGui(guiItems, carriedItem)

        override fun slots(item: BaseSlot, vararg slotIds: Int) {
            slotIds.forEach { slotId ->
                guiItems[slotId] = item
            }
        }

        override fun fill(fillItem: BaseSlot, slotIds: IntRange) {
            slotIds.forEach { slotId ->
                guiItems.putIfAbsent(slotId, fillItem)
            }
        }

        override fun Pair<Int, BaseSlot>.unaryPlus() {
            guiItems[this.first] = this.second
        }
    }

    fun modifyGui(refreshContent: Boolean = true, contentBuilder: EditableGuiBuilder.() -> Unit): SyncGui {
        EditableGuiBuilder().apply(contentBuilder).build().apply {
            guiContent.guiItems += guiItems
            guiContent.carriedItem = carriedItem
        }
        stateId.incrementAndGet()
        if (refreshContent) refreshContentForAll()
        return this
    }

    override fun openFor(
        refreshContent: Boolean,
        vararg users: User
    ): SyncGui {
        users.forEach { user ->
            playersForSync.add(user)
            user.sendPacket(WrapperPlayServerOpenWindow(containerId, guiType.typeId(), title))
        }
        if (refreshContent) refreshContentFor(*users)
        return this
    }

    override fun closeFor(silently: Boolean, vararg users: User): SyncGui {
        users.forEach { user ->
            if (playersForSync.remove(user) && !silently) {
                user.sendPacket(WrapperPlayServerCloseWindow(containerId))
            }
        }
        return this
    }

    override fun closeForAll(): SyncGui {
        closeFor(false, *playersForSync.toTypedArray())
        return this
    }

    override fun refreshContentFor(vararg users: User): SyncGui {
        users.forEach { user ->
            if (playersForSync.contains(user)) {
                user.sendPacket(
                    WrapperPlayServerWindowItems(
                        containerId,
                        stateId.get(),
                        MutableList<ItemStack>(guiType.typeSize()) {
                            guiContent.guiItems[it]?.itemStack ?: ItemStack.EMPTY
                        },
                        guiContent.carriedItem?.itemStack
                    )
                )
            }
        }
        return this
    }

    override fun refreshContentForAll(): SyncGui {
        refreshContentFor(*playersForSync.toTypedArray())
        return this
    }

}