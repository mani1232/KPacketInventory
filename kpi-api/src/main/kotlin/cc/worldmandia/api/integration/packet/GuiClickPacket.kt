package cc.worldmandia.api.integration.packet

import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow

data class GuiClickPacket(
    val user: User,
    val clickPacket: WrapperPlayClientClickWindow,
    var cancelled: Boolean = false,
)
