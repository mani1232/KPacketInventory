package cc.worldmandia.api

import cc.worldmandia.api.gui.BaseGui
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
object InventoryStorage {

    val registeredInventories = ConcurrentHashMap<Int, BaseGui>()

    val inventoryIdsManager = AtomicInt(100)

}