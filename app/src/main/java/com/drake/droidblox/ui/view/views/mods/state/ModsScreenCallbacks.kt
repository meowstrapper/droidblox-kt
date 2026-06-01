package com.drake.droidblox.ui.view.views.mods.state

data class ModsScreenCallbacks(
    val applyMods: (Boolean) -> Unit = {},
    val grantFilesPermission: () -> Unit = {},
    val help: () -> Unit = {},
    val mouseCursor: (String) -> Unit = {},
    val useOldAvatarEditor: (Boolean) -> Unit = {},
    val emulateOldCharacterSounds: (Boolean) -> Unit = {},
    val emojiType: (String) -> Unit = {},
    val useCustomFont: () -> Unit = {}
)