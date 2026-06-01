package com.drake.droidblox.ui.view.views.robloxconfig.state

data class RobloxConfigState(
    val robloxVersion: String? = null, // use this to check if roblox is already patched
    val lastLaunched: Long? = null,
    val robloxNewestVersion: String? = null // for updates
)