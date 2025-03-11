package cc.worldmandia.adventure

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

fun String.minimessage(): Component = MiniMessage.miniMessage().deserialize(this)