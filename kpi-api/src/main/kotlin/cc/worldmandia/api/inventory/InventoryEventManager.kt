package cc.worldmandia.api.inventory

import cc.worldmandia.api.inventory.impl.ImplInventory
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow

interface InventoryEventManager {
    fun pushCloseEventForUser(user: User, wrapper: WrapperPlayClientCloseWindow): Boolean
    fun pushClickEvent(slotId: Int, packet: WrapperPlayClientClickWindow)
    fun onMenuClose(customEvent: (User, WrapperPlayClientCloseWindow) -> Unit): ImplInventory
}