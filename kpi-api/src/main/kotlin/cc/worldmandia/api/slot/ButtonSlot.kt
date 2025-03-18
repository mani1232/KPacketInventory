package cc.worldmandia.api.slot

import cc.worldmandia.api.integration.packet.GuiClickPacket
import com.github.retrooper.packetevents.protocol.item.ItemStack

open class ButtonSlot(
    itemStack: ItemStack,
    open val onClick: (GuiClickPacket) -> Unit,
) : BaseSlot(itemStack) {
    override fun beforeClickEvent(packet: GuiClickPacket) {
        onClick(packet) // This is base button, we don't need change smth
    }
}