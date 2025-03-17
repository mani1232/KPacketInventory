package cc.worldmandia

import cc.worldmandia.api.gui.SyncGui

fun createSyncGui(init: SyncGui.SyncGuiBuilder.() -> Unit) =
    SyncGui.SyncGuiBuilder().apply(init).build()