package cc.worldmandia.api

import cc.worldmandia.api.inventory.BaseInventory
import java.util.concurrent.atomic.AtomicInteger

object InventoryStorage {

    val registeredInventories = mutableMapOf<Int, BaseInventory>()

    val inventoryIdsManager = AtomicInteger(101)

}