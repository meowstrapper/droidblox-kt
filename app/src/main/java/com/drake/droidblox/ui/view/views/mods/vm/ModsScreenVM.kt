package com.drake.droidblox.ui.view.views.mods.vm

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drake.droidblox.datastores.ModsManager
import com.drake.droidblox.texturemods.models.CustomEmoji
import com.drake.droidblox.texturemods.models.CustomMouseCursor
import com.drake.droidblox.texturemods.TextureMods
import com.drake.droidblox.ui.view.views.mods.state.ModsScreenState
import com.drake.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModsScreenVM @Inject constructor(
    val logger: Logger,
    @ApplicationContext val context: Context,
    val textureMods: TextureMods,
    val modsManager: ModsManager
) : ViewModel() {
    companion object {
        private const val TAG = "ModsScreenVM"
    }

    val uiState: StateFlow<ModsScreenState> = modsManager.dataStore.data.map { prefs ->
        ModsScreenState(
            applyMods = prefs[ModsManager.APPLY_MODS] ?: true,
            filesGrantedPermission = true, // TODO
            mouseCursor = prefs[ModsManager.MOUSE_CURSOR] ?: "Default",
            useOldAvatarEditor = prefs[ModsManager.USE_OLD_AVATAR_EDITOR] ?: false,
            emulateOldCharacterSounds = prefs[ModsManager.EMULATE_OLD_CHARACTER_SOUNDS] ?: false,
            emojiType = prefs[ModsManager.PREFERRED_EMOJI_TYPE] ?: "Default"
        )
    }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ModsScreenState()
        )

    fun <T> setSetting(key: Preferences.Key<T>, value: T) = viewModelScope.launch(Dispatchers.IO) {
        modsManager.set(key, value)
    }

    fun openHelp() = context.startActivity(
        Intent(
            Intent.ACTION_VIEW,
            "https://github.com/meowstrapper/DroidBlox/wiki/Modding".toUri()
        ).apply { setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
    )

    fun useOldAvatarBackground(replace: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            textureMods.useOldAvatarBackground(replace)
        }
    }

    fun useOldCharacterSounds(replace: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            textureMods.useOldCharacterSounds(replace)
        }
    }

    fun useCustomFont() {
        viewModelScope.launch(Dispatchers.IO) {
            textureMods.promptUseCustomFont()
        }
    }

    fun replaceCursor(customMouseCursor: CustomMouseCursor) {
        viewModelScope.launch(Dispatchers.IO) {
            textureMods.replaceCursor(customMouseCursor)
        }
    }

    fun replaceEmoji(customEmoji: CustomEmoji) {
        viewModelScope.launch(Dispatchers.IO) {
            textureMods.replaceEmoji(customEmoji)
        }
    }

}