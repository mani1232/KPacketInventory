package cc.worldmandia

import cc.worldmandia.api.integration.PacketEventListener
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority


object KPILib {

    var enableDebug: Boolean = true

    fun registerPacketListener(packetListenerPriority: PacketListenerPriority = PacketListenerPriority.HIGH) {
        if (!PacketEvents.getAPI().isInitialized) return

        PacketEvents.getAPI().eventManager.registerListener(
            PacketEventListener(), packetListenerPriority
        )
    }

}