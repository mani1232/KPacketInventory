package cc.worldmandia.adventure

import cc.worldmandia.api.gui.SyncGui
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import kotlin.apply

fun String.minimessage(): Component = MiniMessage.miniMessage().deserialize(this)

fun SyncGui.SyncGuiBuilder.title(title: String) = apply { this.title = title.minimessage() }