package cc.worldmandia.api.inventory

import cc.worldmandia.api.InventoryStorage
import cc.worldmandia.api.slot.SlotManager
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.uuid.ExperimentalUuidApi

@BaseInventoryDslMarker
abstract class BaseInventory(
    protected val containerId: Int = InventoryStorage.inventoryIdsManager.incrementAndGet(),
    var closeEvent: SlotManager.(User, WrapperPlayClientCloseWindow) -> Unit
) {
    @OptIn(ExperimentalUuidApi::class)
    protected val playersForUpdate: MutableSet<User> = ConcurrentHashMap.newKeySet()
    protected var stateId = AtomicInteger(1)

    init {
        InventoryStorage.registeredInventories[containerId] = this
    }
}