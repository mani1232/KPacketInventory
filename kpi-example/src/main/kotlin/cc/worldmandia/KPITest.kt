package cc.worldmandia

import cc.worldmandia.adventure.title
import cc.worldmandia.api.conversion.convert
import cc.worldmandia.api.conversion.openFor
import cc.worldmandia.api.conversion.slot.BukkitButtonSlot
import cc.worldmandia.api.gui.SyncGui
import cc.worldmandia.api.gui.type.GuiType
import cc.worldmandia.api.slot.BaseSlot
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

class KPITest : JavaPlugin(), Listener {

    lateinit var inventory: SyncGui

    @EventHandler
    fun onAsyncChatEvent(event: AsyncChatEvent) {
        if (event.message() == Component.text("TESTKPIAPI")) {
            inventory.openFor(true,event.player)
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

    lateinit var btn4: BaseSlot
    lateinit var btn2: BaseSlot

    override fun onEnable() {
        PacketEvents.getAPI().init()
        KPILib.registerPacketListener()
        server.pluginManager.registerEvents(this, this)


        btn2 = BukkitButtonSlot(ItemStack(Material.DIAMOND)) { oldBtnClick ->
            oldBtnClick.user.convert()?.sendMessage(Component.text("Btn 2"))
            inventory.modifyGui {
                11 to btn4
            }
        }
        btn4 = BukkitButtonSlot(ItemStack(Material.DIAMOND)) { newBtnClick ->
            newBtnClick.user.convert()?.sendMessage(Component.text("Btn 4"))
            inventory.modifyGui {
                11 to btn2
            }
        }

        inventory = createSyncGui {
            title("KPI Test") // From adventure helpers
            content {
                // Change by Range
                slots(BukkitButtonSlot(ItemStack(Material.DIAMOND)) {
                    it.user.convert()?.sendMessage(Component.text("Btn 1"))
                    inventory.refreshContentFor(it.user)
                }, 0..GuiType.GENERIC_9X6.typeSize() - 20)
                // Change by id
                11 to btn2
                // Fill ranged empty slots
                fill(BukkitButtonSlot(ItemStack(Material.DIAMOND)) {
                    inventory.refreshContentFor(it.user)
                    it.user.convert()?.sendMessage(Component.text("Btn 3"))
                }, 0..GuiType.GENERIC_9X6.typeSize())
            }
        }
    }
}