package cc.worldmandia.api.slot

import cc.worldmandia.api.integration.packet.GuiClickPacket
import com.github.retrooper.packetevents.protocol.item.ItemStack

abstract class BaseSlot(
    open val itemStack: ItemStack
) {
    abstract fun beforeClickEvent(packet: GuiClickPacket)
}