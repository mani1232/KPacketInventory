package cc.worldmandia.api.inventory

import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow

interface InventoryEventManager {
    fun pushCloseEventForUser(user: User, wrapper: WrapperPlayClientCloseWindow): Boolean
    fun pushClickEvent(slotId: Int, packet: WrapperPlayClientClickWindow)
}