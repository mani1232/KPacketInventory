package cc.worldmandia.api.inventory

import cc.worldmandia.api.inventory.impl.ImplInventory
import com.github.retrooper.packetevents.protocol.player.User

interface InventoryPlayerManager {
    fun sendTo(packetPlayer: User, refresh: Boolean = true): ImplInventory
    fun isPlayerRegistered(packetPlayer: User): Boolean
    fun closeFor(packetPlayer: User, pushEvent: Boolean = false): ImplInventory
    fun closeForAll(pushEvent: Boolean = false): ImplInventory
}