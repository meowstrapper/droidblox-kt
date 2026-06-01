package com.drake.droidblox.ui.components.states

data class TextFieldState(
    val text: String = "",
    val onTextChange: (String) -> Unit = {}
)