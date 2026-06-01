package com.drake.droidblox.ui.view.views.integrations.vm

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drake.droidblox.datastores.SettingsManager
import com.drake.droidblox.ui.view.views.integrations.state.IntegrationsScreenState
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
class IntegrationsScreenVM @Inject constructor(
    @ApplicationContext private val context: Context,
    val settingsManager: SettingsManager
) : ViewModel() {
    val uiState: StateFlow<IntegrationsScreenState> = settingsManager.dataStore.data.map { prefs ->
        IntegrationsScreenState(
            enableActivityTracking = prefs[SettingsManager.ENABLE_ACTIVITY_TRACKING] ?: true,
            queryServerLocation = prefs[SettingsManager.QUERY_SERVER_LOCATION] ?: false,
            showGameActivity = prefs[SettingsManager.SHOW_GAME_ACTIVITY] ?: true,
            allowActivityJoining = prefs[SettingsManager.ALLOW_ACTIVITY_JOINING] ?: false,
            showRobloxUser = prefs[SettingsManager.SHOW_ROBLOX_USER] ?: false
        )
    }
        .distinctUntilChanged()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = IntegrationsScreenState()
    )

    fun <T> setSetting(key: Preferences.Key<T>, value: T) = viewModelScope.launch(Dispatchers.IO) {
        settingsManager.set(key, value)
    }

    fun launchRoblox() = context.let {
        com.drake.droidblox.roblox.launchRoblox(context = it)
    }
}