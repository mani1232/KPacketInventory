package cc.worldmandia.api.gui.feature.editable

import cc.worldmandia.api.gui.GuiDslMarker
import cc.worldmandia.api.slot.BaseSlot

@GuiDslMarker
interface IEditableGui {
    fun slots(item: BaseSlot, vararg slotIds: Int)

    fun slots(item: BaseSlot, slotIds: IntRange) {
        slots(item, *slotIds.toList().toIntArray())
    }

    fun fill(fillItem: BaseSlot, slotIds: IntRange)

    operator fun Pair<Int, BaseSlot>.unaryPlus()
}