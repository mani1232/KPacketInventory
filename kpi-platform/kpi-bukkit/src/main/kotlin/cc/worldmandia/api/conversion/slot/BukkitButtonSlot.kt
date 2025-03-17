package cc.worldmandia.api.conversion.slot

import cc.worldmandia.api.conversion.convert
import cc.worldmandia.api.integration.packet.GuiClickPacket
import cc.worldmandia.api.slot.ButtonSlot
import org.bukkit.inventory.ItemStack

data class BukkitButtonSlot(
    val bukkitItemType: ItemStack,
    override val onClick: (GuiClickPacket) -> Unit = {}
): ButtonSlot(bukkitItemType.convert(), onClick)