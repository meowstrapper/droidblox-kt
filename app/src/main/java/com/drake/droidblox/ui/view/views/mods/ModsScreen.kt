/*
 * TODO:
 *  Add a choose zip file, Fix old avatar background, Fix custom fonts, Add a remove custom fonts
 */
package com.drake.droidblox.ui.view.views.mods

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.drake.droidblox.datastores.ModsManager
import com.drake.droidblox.texturemods.models.CustomEmoji
import com.drake.droidblox.texturemods.models.CustomMouseCursor
import com.drake.droidblox.ui.components.BasicScreen
import com.drake.droidblox.ui.components.ExtendedButton
import com.drake.droidblox.ui.components.ExtendedDropdown
import com.drake.droidblox.ui.components.ExtendedSwitch
import com.drake.droidblox.ui.components.SectionText
import com.drake.droidblox.ui.view.views.mods.state.ModsScreenCallbacks
import com.drake.droidblox.ui.view.views.mods.vm.ModsScreenVM
import com.drake.droidblox.ui.view.views.mods.state.ModsScreenState

@Composable
fun ModsScreen(
    navController: NavController? = null,
    state: ModsScreenState = ModsScreenState(),
    callback: ModsScreenCallbacks = ModsScreenCallbacks()
) {
    BasicScreen(
        name = "Mods",
        navController = navController
    ) {
        ExtendedSwitch(
            title = "Apply mods",
            subtitle = "Allow DroidBlox to apply texture mods upon launching Roblox.",
            enabled = state.applyMods,
            onClick = callback.applyMods
        )
        if (!state.filesGrantedPermission) {
            ExtendedButton(
                title = "Grant files permission",
                subtitle = "Granting permission allows you and DroidBlox to manage texture mods.",
                onClick = callback.grantFilesPermission
            )
        }
        ExtendedButton(
            title = "Help",
            subtitle = "See info about managing and creating mods.",
            onClick = callback.help
        )

        SectionText("Presets")

        ExtendedDropdown(
            title = "Mouse cursor",
            subtitle = "Choose between using two classic Roblox cursor styles. (When using OTG Mouse)",
            default = state.mouseCursor,
            items = listOf(
                "Default",
                "2006",
                "2013"
            ),
            onChoose = callback.mouseCursor,
            disabled = !state.filesGrantedPermission
        )
        ExtendedSwitch(
            title = "Use old avatar editor background",
            subtitle = "Bring back the old avatar editor background used in the Roblox app prior to 2020.",
            enabled = state.useOldAvatarEditor,
            onClick = callback.useOldAvatarEditor,
            disabled = !state.filesGrantedPermission
        )

        ExtendedSwitch(
            title = "Emulate old character sounds",
            subtitle = "An attempt to roughly bring back the character sounds used to prior to 2014.",
            enabled = state.emulateOldCharacterSounds,
            onClick = callback.emulateOldCharacterSounds,
            disabled = !state.filesGrantedPermission
        )

        ExtendedDropdown(
            title = "Preferred emoji type",
            subtitle = "Choose which type of emoji should Roblox use.",
            default = state.emojiType,
            items = listOf(
                "Default",
                "Android",
                "Catmoji",
                "Windows 11",
                "Windows 10",
                "Windows 8"
            ),
            onChoose = callback.emojiType,
            disabled = !state.filesGrantedPermission
        )

        SectionText("Miscellaneous")

        ExtendedButton(
            title = "Use custom font",
            subtitle = "Font size can be adjusted in the Engine Settings tab.",
            onClick = callback.useCustomFont,
            disabled = !state.filesGrantedPermission
        )

    }
}

@Composable
fun ModsScreenImpl(
    navController: NavController
) {
    val viewModel: ModsScreenVM = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ModsScreen(
        navController = navController,
        state = uiState,
        callback = ModsScreenCallbacks(
            applyMods = {
                viewModel.setSetting(
                    key = ModsManager.APPLY_MODS,
                    value = it
                )
            },
            help = { viewModel.openHelp() },
            mouseCursor = {
                when (it) {
                    "Default" -> {
                        viewModel.replaceCursor(CustomMouseCursor.DEFAULT)
                        viewModel.setSetting(
                            key = ModsManager.MOUSE_CURSOR,
                            value = it
                        )
                    }

                    "2006" -> {
                        viewModel.replaceCursor(CustomMouseCursor.PRIOR2006)
                        viewModel.setSetting(
                            key = ModsManager.MOUSE_CURSOR,
                            value = it
                        )
                    }

                    "2013" -> {
                        viewModel.replaceCursor(CustomMouseCursor.PRIOR2013)
                        viewModel.setSetting(
                            key = ModsManager.MOUSE_CURSOR,
                            value = it
                        )
                    }
                }
            },
            useOldAvatarEditor = {
                viewModel.useOldAvatarBackground(replace = it)
                viewModel.setSetting(
                    key = ModsManager.USE_OLD_AVATAR_EDITOR,
                    value = it
                )
            },
            emulateOldCharacterSounds = {
                viewModel.useOldCharacterSounds(replace = it)
                viewModel.setSetting(
                    key = ModsManager.EMULATE_OLD_CHARACTER_SOUNDS,
                    value = it
                )
            },
            emojiType = {
                when (it) {
                    "Default" -> {
                        viewModel.replaceEmoji(CustomEmoji.DEFAULT)
                        viewModel.setSetting(
                            key = ModsManager.PREFERRED_EMOJI_TYPE,
                            value = it
                        )
                    }

                    "Android" -> {
                        viewModel.replaceEmoji(CustomEmoji.ANDROID)
                        viewModel.setSetting(
                            key = ModsManager.PREFERRED_EMOJI_TYPE,
                            value = it
                        )
                    }

                    "Catmoji" -> {
                        viewModel.replaceEmoji(CustomEmoji.CATMOJI)
                        viewModel.setSetting(
                            key = ModsManager.PREFERRED_EMOJI_TYPE,
                            value = it
                        )
                    }

                    "Windows 11" -> {
                        viewModel.replaceEmoji(CustomEmoji.WINDOWS11)
                        viewModel.setSetting(
                            key = ModsManager.PREFERRED_EMOJI_TYPE,
                            value = it
                        )
                    }

                    "Windows 10" -> {
                        viewModel.replaceEmoji(CustomEmoji.WINDOWS10)
                        viewModel.setSetting(
                            key = ModsManager.PREFERRED_EMOJI_TYPE,
                            value = it
                        )
                    }

                    "Windows 8" -> {
                        viewModel.replaceEmoji(CustomEmoji.WINDOWS8)
                        viewModel.setSetting(
                            key = ModsManager.PREFERRED_EMOJI_TYPE,
                            value = it
                        )
                    }
                }
            },
            useCustomFont = { viewModel.useCustomFont() }
        )
    )
}
@Preview
@Composable
private fun PreviewModsScreen() {
    ModsScreen()
}