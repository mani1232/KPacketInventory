package cc.worldmandia.api.inventory.type

enum class InventoryType(val type: Int, val size: Int) : BaseType {
    GENERIC_9X1(0, 9), // A 1-row inventory, not used by the notchian server.
    GENERIC_9X2(1, 18), // A 2-row inventory, not used by the notchian server.
    GENERIC_9X3(2, 27), // General-purpose 3-row inventory. Used by Chest, minecart with chest, ender chest, and barrel.
    GENERIC_9X4(3, 36), // A 4-row inventory, not used by the notchian server.
    GENERIC_9X5(4, 45), // A 5-row inventory, not used by the notchian server.
    GENERIC_9X6(5, 54), // General-purpose 6-row inventory, used by large chests.

    GENERIC_3X3(6, 9),  // General-purpose 3-by-3 square inventory, used by Dispenser and Dropper.
    CRAFTER_3X3(7, 9),  // Crafter.

    ANVIL(8, 3),        // Anvil.
    BEACON(9, 1),       // Beacon.
    BLAST_FURNACE(10, 1), // Blast Furnace.
    BREWING_STAND(11, 5), // Brewing stand.
    CRAFTING(12, 9),    // Crafting table.
    ENCHANTMENT(13, 1), // Enchantment table.
    FURNACE(14, 1),     // Furnace.
    GRINDSTONE(15, 1),  // Grindstone.
    HOPPER(16, 5),      // Hopper or minecart with hopper.
    LECTERN(17, 3),     // Lectern.
    LOOM(18, 4),        // Loom.
    MERCHANT(19, 1),    // Villager, Wandering Trader.
    SHULKER_BOX(20, 27), // Shulker box.
    SMITHING(21, 1),    // Smithing Table.
    SMOKER(22, 1),      // Smoker.
    CARTOGRAPHY(23, 3), // Cartography Table.
    STONECUTTER(24, 1); // Stonecutter.

    override fun typeId(): Int {
        return type
    }

    override fun typeSize(): Int {
        return size
    }
}
