package cc.worldmandia.api.integration.packet

import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow

data class GuiClosePacket(
    val user: User,
    val closePacket: WrapperPlayClientCloseWindow,
    var cancelled: Boolean = false
)
