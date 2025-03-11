package cc.worldmandia.api.inventory

import cc.worldmandia.api.InventoryStorage
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@BaseInventoryDslMarker
abstract class BaseInventory(
    open val containerId: Int,
    protected var closeEvent: (User, WrapperPlayClientCloseWindow) -> Unit = { _, _ -> }
) {
    protected val playersForUpdate: MutableSet<User> = ConcurrentHashMap.newKeySet()
    protected var stateId = AtomicInteger(1)

    init {
        InventoryStorage.registeredInventories[containerId] = this
    }
}