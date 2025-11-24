package com.drake.droidblox.backend.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SettingsManager(
    context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // todo: improve this by not doing DRY
    var enableActivityTracking: Boolean
        get() = sharedPreferences.getBoolean("enableActivityTracking", true)
        set(value) = sharedPreferences.edit { putBoolean("enableActivityTracking", value) }
    var showServerLocation: Boolean
        get() = sharedPreferences.getBoolean("showServerLocation", false)
        set(value) = sharedPreferences.edit { putBoolean("showServerLocation", value) }
    var token: String?
        get() = sharedPreferences.getString("token", null)
        set(value) = sharedPreferences.edit { putString("token", value) }
    var showGameActivity: Boolean
        get() = sharedPreferences.getBoolean("showGameActivity", true)
        set(value: Boolean) = sharedPreferences.edit { putBoolean("showGameActivity", value) }
    var allowActivityJoining: Boolean
        get() = sharedPreferences.getBoolean("allowActivityJoining", false)
        set(value) = sharedPreferences.edit { putBoolean("allowActivityJoining", value) }
    var showRobloxUser: Boolean
        get() = sharedPreferences.getBoolean("showRobloxUser", false)
        set(value) = sharedPreferences.edit { putBoolean("showRobloxUser", value) }
}