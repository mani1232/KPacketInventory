package cc.worldmandia.api.conversion.slot

import cc.worldmandia.api.conversion.convert
import cc.worldmandia.api.slot.ButtonSlot
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow
import org.bukkit.inventory.ItemStack

data class BukkitButtonSlot(
    val bukkitItemType: ItemStack,
    override var canceledClickEvent: Boolean = true,
    override val onClick: (WrapperPlayClientClickWindow) -> Unit
): ButtonSlot(bukkitItemType.convert(), canceledClickEvent, onClick)