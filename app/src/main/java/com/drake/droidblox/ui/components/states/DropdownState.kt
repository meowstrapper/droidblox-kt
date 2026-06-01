package com.drake.droidblox.ui.components.states

data class DropdownState(
    val text: String = "",
    val onChoose: (String) -> Unit = {}
)