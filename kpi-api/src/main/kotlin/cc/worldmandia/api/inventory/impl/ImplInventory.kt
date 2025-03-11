package cc.worldmandia.api.inventory.impl

import cc.worldmandia.api.InventoryStorage
import cc.worldmandia.api.inventory.type.BaseType
import cc.worldmandia.api.inventory.type.InventoryType
import cc.worldmandia.api.slot.ButtonSlot
import cc.worldmandia.api.slot.SlotManager
import com.github.retrooper.packetevents.protocol.item.ItemStack
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems
import net.kyori.adventure.text.Component

open class ImplInventory(
    private var title: Component,
    private val type: BaseType = InventoryType.GENERIC_9X6,
    override val containerId: Int = InventoryStorage.inventoryIdsManager.incrementAndGet()
) : SlotManager(containerId) {

    override fun pushCloseEventForUser(user: User, wrapper: WrapperPlayClientCloseWindow): Boolean {
        return if (playersForUpdate.remove(user)) {
            closeEvent.invoke(user, wrapper)
            true
        } else false
    }

    override fun pushClickEvent(slotId: Int, packet: WrapperPlayClientClickWindow) {
        (slots[slotId] as? ButtonSlot)?.apply {
            this.onClick.invoke(packet)
        }
    }

    override fun sendTo(packetPlayer: User, refresh: Boolean): ImplInventory {
        playersForUpdate.add(packetPlayer)
        packetPlayer.sendPacket(WrapperPlayServerOpenWindow(containerId, type.typeId(), title))
        if (refresh) {
            refreshSlots(packetPlayer)
        }
        return this
    }

    override fun isPlayerRegistered(packetPlayer: User): Boolean {
        return playersForUpdate.contains(packetPlayer)
    }

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
            refreshSlots(user)
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

    override fun onMenuClose(customEvent: (User, WrapperPlayClientCloseWindow) -> Unit): ImplInventory {
        closeEvent = customEvent
        return this
    }


}