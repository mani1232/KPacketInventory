package cc.worldmandia.api.integration

import cc.worldmandia.KPILib
import cc.worldmandia.api.InventoryStorage
import cc.worldmandia.api.gui.SyncGui
import cc.worldmandia.api.gui.feature.SlotManaged
import cc.worldmandia.api.integration.packet.GuiClickPacket
import cc.worldmandia.api.integration.packet.GuiClosePacket
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.UserDisconnectEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow
import kotlin.jvm.optionals.getOrNull

class PacketEventListener : PacketListener {

    override fun onUserDisconnect(event: UserDisconnectEvent) {
        InventoryStorage.registeredInventories.values.filterIsInstance<SyncGui>().forEach {
            it.closeFor(true, event.user)
        }
    }

    override fun onPacketReceive(event: PacketReceiveEvent) {
        when (event.packetType) {
            PacketType.Play.Client.CLICK_WINDOW -> {
                WrapperPlayClientClickWindow(event).apply {
                    if (KPILib.enableDebug) {
                        println("---------------")
                        println("Click type ${windowClickType.name}")
                        println("Button Id $button")
                        println("Clicked slot id $slot")
                        println("Carried item ${carriedItemStack.type.name}")
                        println("Size of changed slots ${slots.getOrNull()?.size ?: -1}")
                        println(
                            "Changed slots ${
                                slots.getOrNull()
                                    ?.map { entry -> "${entry.key} to ${entry.value.type.name} and ${entry.value.amount}" } ?: -1
                            }")
                    }

                    InventoryStorage.registeredInventories[windowId]?.let { menu ->
                        if (menu is SlotManaged) event.isCancelled = GuiClickPacket(event.user, this).also {
                            menu.processSlots(it)
                        }.cancelled
                    }
                }
            }

            PacketType.Play.Client.CLOSE_WINDOW -> {
                WrapperPlayClientCloseWindow(event).apply {
                    InventoryStorage.registeredInventories[windowId]?.let { menu ->
                        event.isCancelled = GuiClosePacket(event.user, this).also {
                            menu.closeEvent.invoke(menu, it)
                        }.cancelled
                    }
                }
            }
        }
    }
}