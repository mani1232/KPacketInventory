package cc.worldmandia.api.inventory.impl

import cc.worldmandia.api.InventoryHelperDslMarker
import cc.worldmandia.api.inventory.type.BaseType
import cc.worldmandia.api.inventory.type.InventoryType
import cc.worldmandia.api.slot.ButtonSlot
import cc.worldmandia.api.slot.SlotManager
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.item.ItemStack
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems
import net.kyori.adventure.text.Component
import kotlin.uuid.ExperimentalUuidApi

open class ImplInventory(
    private var title: Component,
    override val type: BaseType,
    closeEvent: SlotManager.(User, WrapperPlayClientCloseWindow) -> Unit = { _, _ -> }
) : SlotManager(type, closeEvent) {

    @InventoryHelperDslMarker
    class ImplInventoryBuilder {
        var title: Component = Component.text("ChangeMe")
        var inventoryType: InventoryType = InventoryType.GENERIC_9X6
        var closeEvent: SlotManager.(User, WrapperPlayClientCloseWindow) -> Unit = { _, _ -> }

        fun build(): ImplInventory {
            return ImplInventory(title, inventoryType, closeEvent = closeEvent)
        }
    }

    override fun pushCloseEventForUser(user: User, wrapper: WrapperPlayClientCloseWindow): Boolean {
        return if (playersForUpdate.remove(user)) {
            closeEvent.invoke(this, user, wrapper)
            true
        } else false
    }

    override fun pushClickEvent(slotId: Int, packet: WrapperPlayClientClickWindow) {
        (slots[slotId] as? ButtonSlot)?.apply {
            this.onClick.invoke(packet)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun sendTo(packetPlayer: User, refresh: Boolean): ImplInventory {
        playersForUpdate.add(packetPlayer)
        packetPlayer.sendPacket(WrapperPlayServerOpenWindow(containerId, type.typeId(), title))
        if (refresh) {
            refreshSlots(packetPlayer)
        }
        return this
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun isPlayerRegistered(packetPlayer: User): Boolean {
        return playersForUpdate.contains(packetPlayer)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun closeFor(packetPlayer: User, pushEvent: Boolean): ImplInventory {
        playersForUpdate.remove(packetPlayer)
        WrapperPlayClientCloseWindow(containerId).let {
            packetPlayer.sendPacket(it)
            if (pushEvent) pushCloseEventForUser(packetPlayer, it)
        }
        return this
    }

    override fun closeForAll(
        pushEvent: Boolean
    ): ImplInventory {
        playersForUpdate.forEach {
            closeFor(it, pushEvent)
        }
        return this
    }

    override fun modifySlots(modifiedSlots: SlotManager.() -> Unit): ImplInventory {
        modifiedSlots.invoke(this)
        pendingUpdate.forEach {
            slots[it.key] = it.value
        }
        playersForUpdate.forEach { user ->
            refreshSlots(PacketEvents.getAPI().playerManager.getUser(user))
        }
        pendingUpdate.clear()
        return this
    }

    override fun refreshSlots(user: User): ImplInventory {
        user.sendPacket(
            WrapperPlayServerWindowItems(
                containerId,
                stateId.incrementAndGet(),
                MutableList<ItemStack>(type.typeSize()) { slots[it]?.itemType ?: ItemStack.EMPTY },
                carriedItem
            )
        )
        return this
    }
}