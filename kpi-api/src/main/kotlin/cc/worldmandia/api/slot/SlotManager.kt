package cc.worldmandia.api.slot

import cc.worldmandia.api.inventory.BaseInventory
import cc.worldmandia.api.inventory.InventoryEventManager
import cc.worldmandia.api.inventory.InventoryPlayerManager
import com.github.retrooper.packetevents.protocol.item.ItemStack
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow
import java.util.concurrent.ConcurrentHashMap

abstract class SlotManager(
    containerId: Int,
    closeEvent: SlotManager.(User, WrapperPlayClientCloseWindow) -> Unit
) : BaseInventory(containerId, closeEvent), InventoryPlayerManager,
    InventoryEventManager {

    protected val slots = ConcurrentHashMap<Int, BaseSlot>()
    protected val pendingUpdate = ConcurrentHashMap<Int, BaseSlot>()

    var carriedItem: ItemStack? = null

    var canceledCloseEvent = false

    fun isCancelled(slotId: Int): Boolean {
        return (slots[slotId] as? ButtonSlot)?.canceledClickEvent != false
    }

    fun slots(newItem: BaseSlot, vararg slotIds: Int) {
        slotIds.forEach { slotId ->
            pendingUpdate.put(slotId, newItem)
        }
    }

    abstract fun modifySlots(modifiedSlots: SlotManager.() -> Unit): BaseInventory

    abstract fun refreshSlots(user: User): BaseInventory

    operator fun Slot.unaryPlus() {
        slots(slot, slotId)
    }
}