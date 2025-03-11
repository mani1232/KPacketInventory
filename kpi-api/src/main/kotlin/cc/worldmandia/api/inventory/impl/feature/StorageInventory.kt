package cc.worldmandia.api.inventory.impl.feature

import cc.worldmandia.api.inventory.impl.ImplInventory
import cc.worldmandia.api.inventory.type.BaseType
import net.kyori.adventure.text.Component

class StorageInventory(title: Component, type: BaseType) : ImplInventory(title, type)