package cc.worldmandia.api.conversion

import cc.worldmandia.api.inventory.impl.ImplInventory
import cc.worldmandia.api.slot.SlotManager
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.player.User
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import com.github.retrooper.packetevents.protocol.item.ItemStack as PacketItemStack

fun SlotManager.sendTo(bukkitPlayer: Player, refresh: Boolean = true): ImplInventory =
    sendTo(bukkitPlayer.convert(), refresh)

fun SlotManager.closeFor(bukkitPlayer: Player, pushEvent: Boolean = false): ImplInventory =
    closeFor(bukkitPlayer.convert(), pushEvent)

fun ItemStack.convert(): PacketItemStack = SpigotConversionUtil.fromBukkitItemStack(this)

fun Player.convert(): User = PacketEvents.getAPI().playerManager.getUser(this)
