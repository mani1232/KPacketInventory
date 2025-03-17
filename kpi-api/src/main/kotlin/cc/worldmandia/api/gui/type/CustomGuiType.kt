package cc.worldmandia.api.gui.type

data class CustomGuiType(val type: Int, val size: Int) : BaseType {
    override fun typeId(): Int {
        return type
    }

    override fun typeSize(): Int {
        return size
    }
}