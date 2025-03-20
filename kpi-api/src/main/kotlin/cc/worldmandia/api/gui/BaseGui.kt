package cc.worldmandia.api.gui

import cc.worldmandia.api.InventoryStorage
import cc.worldmandia.api.gui.type.BaseType
import cc.worldmandia.api.integration.packet.GuiClosePacket
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
abstract class BaseGui(
    val guiType: BaseType,
    protected val containerId: Int = InventoryStorage.inventoryIdsManager.fetchAndAdd(1),
    var closeEvent: BaseGui.(GuiClosePacket) -> Unit = { }
) {

    protected var stateId = AtomicInt(1)

    init {
        InventoryStorage.registeredInventories[containerId] = this
    }
}