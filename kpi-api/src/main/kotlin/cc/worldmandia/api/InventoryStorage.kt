package cc.worldmandia.api

import cc.worldmandia.api.gui.BaseGui
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

object InventoryStorage {

    val registeredInventories = ConcurrentHashMap<Int, BaseGui>()

    val inventoryIdsManager = AtomicInteger(101)

}