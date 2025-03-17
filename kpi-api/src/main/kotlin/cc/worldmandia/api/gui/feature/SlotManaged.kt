package cc.worldmandia.api.gui.feature

import cc.worldmandia.api.integration.packet.GuiClickPacket

interface SlotManaged {
    fun processSlots(packet: GuiClickPacket)
}