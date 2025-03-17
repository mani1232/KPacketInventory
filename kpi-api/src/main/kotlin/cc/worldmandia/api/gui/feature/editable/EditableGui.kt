package cc.worldmandia.api.gui.feature.editable

import cc.worldmandia.api.slot.BaseSlot
import java.util.concurrent.ConcurrentHashMap

data class EditableGui(
    val guiItems: ConcurrentHashMap<Int, BaseSlot>,
    var carriedItem: BaseSlot? = null
)