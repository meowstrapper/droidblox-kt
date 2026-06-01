package com.drake.droidblox.ui.view.views.robloxconfig.state

data class RobloxConfigCallbacks(
    val launchRoblox: () -> Unit = {},
    val updateRoblox: () -> Unit = {},
    val uninstallRoblox: () -> Unit = {},
    val patchRoblox: () -> Unit = {},
    val configureKeystore: () -> Unit = {}
)
