package cc.worldmandia.api.gui.feature

import cc.worldmandia.api.gui.BaseGui
import com.github.retrooper.packetevents.protocol.player.User

interface UserBasedGui {
    fun openFor(refreshContent: Boolean = true, vararg users: User): BaseGui
    fun closeFor(silently: Boolean = false, vararg users: User): BaseGui
    fun closeForAll(): BaseGui
    fun refreshContentFor(vararg users: User): BaseGui
    fun refreshContentForAll(): BaseGui
}