package cc.worldmandia.api.slot

import com.github.retrooper.packetevents.protocol.item.ItemStack
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow

open class ButtonSlot(
    override val itemType: ItemStack,
    open var canceledClickEvent: Boolean = true,
    open val onClick: (WrapperPlayClientClickWindow) -> Unit
) : BaseSlot(itemType)