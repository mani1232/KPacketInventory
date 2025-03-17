package cc.worldmandia.api.gui

import cc.worldmandia.api.InventoryStorage
import cc.worldmandia.api.gui.type.BaseType
import cc.worldmandia.api.integration.packet.GuiClosePacket
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseGui(
    val guiType: BaseType,
    protected val containerId: Int = InventoryStorage.inventoryIdsManager.incrementAndGet(),
    var closeEvent: BaseGui.(GuiClosePacket) -> Unit = { }
) {

    protected var stateId = AtomicInteger(1)

    init {
        InventoryStorage.registeredInventories[containerId] = this
    }
}