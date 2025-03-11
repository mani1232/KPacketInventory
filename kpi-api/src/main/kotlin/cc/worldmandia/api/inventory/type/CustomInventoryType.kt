package cc.worldmandia.api.inventory.type

data class CustomInventoryType(val type: Int, val size: Int) : BaseType {
    override fun typeId(): Int {
        return type
    }

    override fun typeSize(): Int {
        return size
    }
}