package cc.worldmandia.api.inventory

import cc.worldmandia.api.InventoryStorage
import cc.worldmandia.api.slot.SlotManager
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@BaseInventoryDslMarker
abstract class BaseInventory(
    open val containerId: Int,
    var closeEvent: SlotManager.(User, WrapperPlayClientCloseWindow) -> Unit
) {
    protected val playersForUpdate: MutableSet<User> = ConcurrentHashMap.newKeySet()
    protected var stateId = AtomicInteger(1)

    init {
        InventoryStorage.registeredInventories[containerId] = this
    }
}