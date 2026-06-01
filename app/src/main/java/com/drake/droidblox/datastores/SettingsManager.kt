package com.drake.droidblox.datastores

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    val dataStore: DataStore<Preferences>
) : Defaults(dataStore) {
    companion object {
        private const val TAG = "SettingsManager"

        val ENABLE_ACTIVITY_TRACKING            = booleanPreferencesKey("enableActivityTracking")
        val QUERY_SERVER_LOCATION               = booleanPreferencesKey("queryServerLocation")
        val TOKEN                               = stringPreferencesKey("token")
        val SHOW_GAME_ACTIVITY                  = booleanPreferencesKey("showGameActivity")
        val ALLOW_ACTIVITY_JOINING              = booleanPreferencesKey("allowActivityJoining")
        val SHOW_ROBLOX_USER                    = booleanPreferencesKey("showRobloxUser")
    }
}