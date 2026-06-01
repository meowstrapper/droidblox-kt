package com.drake.droidblox.ui.view.views.integrations.state

data class IntegrationsScreenCallbacks(
    val launchRoblox: () -> Unit = {},
    val enableActivityTracking: (Boolean) -> Unit = {},
    val queryServerLocation: (Boolean) -> Unit = {},
    val showGameActivity: (Boolean) -> Unit = {},
    val allowActivityJoining: (Boolean) -> Unit = {},
    val showRobloxUser: (Boolean) -> Unit = {}
)