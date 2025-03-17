package cc.worldmandia.api.conversion

import cc.worldmandia.api.gui.SyncGui
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.player.User
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import com.github.retrooper.packetevents.protocol.item.ItemStack as PacketItemStack

fun SyncGui.openFor(refresh: Boolean = true, vararg bukkitPlayer: Player): SyncGui =
    openFor(refresh, *bukkitPlayer.map { it.convert() }.toTypedArray())

fun SyncGui.closeFor(silently: Boolean = false, vararg bukkitPlayer: Player): SyncGui =
    closeFor(silently, *bukkitPlayer.map { it.convert() }.toTypedArray())

fun ItemStack.convert(): PacketItemStack = SpigotConversionUtil.fromBukkitItemStack(this)

fun Player.convert(): User = PacketEvents.getAPI().playerManager.getUser(this)

fun User.convert(): Player? = Bukkit.getPlayer(this.uuid)