package cc.worldmandia

import cc.worldmandia.api.inventory.impl.ImplInventory

fun createBaseInventory(builder: ImplInventory.ImplInventoryBuilder.() -> Unit) =
    ImplInventory.ImplInventoryBuilder().apply(builder).build()