package com.drake.droidblox.ui.view.views.mods.state

data class ModsScreenState(
    val applyMods: Boolean = true,
    val filesGrantedPermission: Boolean = false,
    val mouseCursor: String = "Default",
    val useOldAvatarEditor: Boolean = false,
    val emulateOldCharacterSounds: Boolean = false,
    val emojiType: String = "Default"
)