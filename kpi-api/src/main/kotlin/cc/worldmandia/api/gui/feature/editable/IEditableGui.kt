package cc.worldmandia.api.gui.feature.editable

import cc.worldmandia.api.gui.GuiDslMarker
import cc.worldmandia.api.slot.BaseSlot
import com.github.retrooper.packetevents.protocol.item.type.ItemType

@GuiDslMarker
interface IEditableGui {
    fun slots(slot: BaseSlot, vararg slotIds: Int)

    fun slots(slot: BaseSlot, slotIds: IntRange) {
        slots(slot, *slotIds.toList().toIntArray())
    }

    fun replaceByType(slot: BaseSlot, vararg types: ItemType)

    fun fill(fillSlot: BaseSlot, slotIds: IntRange)

    operator fun Pair<Int, BaseSlot>.unaryPlus()
}