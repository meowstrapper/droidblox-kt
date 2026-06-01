package com.drake.droidblox.ui.view.views.integrations.state

data class IntegrationsScreenState(
    val enableActivityTracking: Boolean = true,
    val queryServerLocation: Boolean = false,
    val showGameActivity: Boolean = true,
    val allowActivityJoining: Boolean = false,
    val showRobloxUser: Boolean = false
)