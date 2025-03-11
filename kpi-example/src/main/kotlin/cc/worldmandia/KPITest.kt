package cc.worldmandia

import cc.worldmandia.adventure.minimessage
import cc.worldmandia.api.conversion.convert
import cc.worldmandia.api.conversion.sendTo
import cc.worldmandia.api.conversion.slot.BukkitButtonSlot
import cc.worldmandia.api.inventory.impl.ImplInventory
import cc.worldmandia.api.slot.ButtonSlot
import cc.worldmandia.api.slot.Slot
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.settings.PacketEventsSettings
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class KPITest: JavaPlugin(), Listener {

    @EventHandler
    fun onAsyncChatEvent(event: AsyncChatEvent) {
        if (event.message() == Component.text("TESTKPIAPI")) {
            ImplInventory("Test".minimessage()).modifySlots {
                +Slot(1, BukkitButtonSlot(ItemStack(Material.TNT)) { _ ->
                    logger.info { "Button clicked! 1" }
                })
                slots(ButtonSlot(ItemStack(Material.BIRCH_WOOD).convert()) { _ ->
                    logger.info { "Button clicked! 2" }
                }, 2, 0, 3)
            }.onMenuClose { user, _ ->
                logger.info { "Menu closed! ${user.name}" }
            }.sendTo(event.player)
        }
    }

    override fun onLoad() {
        PacketEvents.setAPI(
            SpigotPacketEventsBuilder.build(
                this,
                PacketEventsSettings().checkForUpdates(false).debug(true).fullStackTrace(true)
            )
        )
        PacketEvents.getAPI().load()
    }

    override fun onEnable() {
        PacketEvents.getAPI().init()
        KPILib.registerPacketListener()
        server.pluginManager.registerEvents(this, this)
    }
}