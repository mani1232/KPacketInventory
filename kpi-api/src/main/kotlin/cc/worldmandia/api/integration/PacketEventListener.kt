package cc.worldmandia.api.integration

import cc.worldmandia.api.InventoryStorage
import cc.worldmandia.api.inventory.impl.ImplInventory
import cc.worldmandia.api.slot.SlotManager
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow

class PacketEventListener : PacketListener {

    override fun onPacketReceive(event: PacketReceiveEvent) {
        when (event.packetType) {
            PacketType.Play.Client.CLICK_WINDOW -> {
                WrapperPlayClientClickWindow(event).also { packet ->
                    event.isCancelled = (InventoryStorage.registeredInventories.entries.firstOrNull {
                        it.key == packet.windowId || (it.value is SlotManager && (it.value as SlotManager).isPlayerRegistered(
                            event.user
                        ))
                    })?.let {
                        if (it.value is SlotManager) {
                            (it.value as SlotManager).let { manager ->
                                manager.pushClickEvent(packet.slot, packet)
                                manager.isCancelled(packet.slot).apply {
                                    if (this) manager.refreshSlots(event.user)
                                }
                            }
                        } else false
                    } == true
                }
            }

            PacketType.Play.Client.CLOSE_WINDOW -> {
                WrapperPlayClientCloseWindow(event).also { packet ->
                    InventoryStorage.registeredInventories.values.filterIsInstance<SlotManager>().forEach {
                        if (it.pushCloseEventForUser(event.user, packet) && it is ImplInventory) {
                            event.isCancelled = it.canceledCloseEvent
                        }
                    }
                }
            }
        }


    }
}