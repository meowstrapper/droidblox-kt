package com.drake.droidblox.ui.components.states

data class SwitchState(
    val enabled: Boolean = false,
    val onClick: (Boolean) -> Unit = {}
)
