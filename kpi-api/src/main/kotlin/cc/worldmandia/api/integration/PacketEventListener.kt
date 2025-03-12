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
                    InventoryStorage.registeredInventories[packet.windowId]?.let {
                        if (it is SlotManager && it.isPlayerRegistered(event.user) && packet.slot < it.type.typeSize()
                        ) {

                            it.pushClickEvent(packet.slot, packet)
                            event.isCancelled = it.isCancelled(packet.slot).apply {
                                if (this) {
                                    it.refreshSlots(event.user)
                                }
                            }
                        } else if (it is SlotManager && packet.slot > it.type.typeSize() && packet.windowClickType == WrapperPlayClientClickWindow.WindowClickType.PICKUP && packet.button < 1) {
                            it.carriedItem = packet.carriedItemStack
                        }
                    }
                }
            }

            PacketType.Play.Client.CLOSE_WINDOW -> {
                WrapperPlayClientCloseWindow(event).also { packet ->
                    InventoryStorage.registeredInventories.values.filterIsInstance<SlotManager>().forEach {
                        if (it.pushCloseEventForUser(
                                event.user, packet
                            ) && it is ImplInventory && it.canceledCloseEvent
                        ) {
                            event.isCancelled = true
                            it.sendTo(event.user)
                        }
                    }
                }
            }
        }


    }
}