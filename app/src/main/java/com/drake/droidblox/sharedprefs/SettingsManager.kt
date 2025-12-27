package com.drake.droidblox.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.drake.droidblox.logger.AndroidLogger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SettingsManager(
    context: Context
) {
    companion object {
        private const val TAG = "DBSettingsManager"
        private val logger = AndroidLogger
    }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private class BooleanBased(
        private val key: String,
        private val default: Boolean
    ): ReadWriteProperty<SettingsManager, Boolean> {
        override fun getValue(thisRef: SettingsManager, property: KProperty<*>): Boolean {
            val value = thisRef.sharedPreferences.getBoolean(key, default)
            logger.d(TAG, "get $key = $value")
            return value
        }

        override fun setValue(thisRef: SettingsManager, property: KProperty<*>, value: Boolean) {
            logger.d(TAG, "set $key = $value")
            thisRef.sharedPreferences.edit { putBoolean(key, value) }
        }
    }
    private class StringBased(
        private val key: String,
        private val default: String?,
        private val redacted: Boolean? = false
    ): ReadWriteProperty<SettingsManager, String?> {
        override fun getValue(thisRef: SettingsManager, property: KProperty<*>): String? {
            val value = thisRef.sharedPreferences.getString(key, default)
            logger.d(TAG, "get $key = ${if (redacted == true) "REDACTED" else value}")
            return value
        }

        override fun setValue(thisRef: SettingsManager, property: KProperty<*>, value: String?) {
            logger.d(TAG, "set $key = ${if (redacted == true) "REDACTED" else value}")
            thisRef.sharedPreferences.edit { putString(key, value) }
        }
    }
    var enableActivityTracking by BooleanBased("enableActivityTracking", true)
    var showServerLocation by BooleanBased("showServerLocation", false)
    var token by StringBased("token", null, true)
    var showGameActivity by BooleanBased("showGameActivity", true)
    var allowActivityJoining by BooleanBased("allowActivityJoining", false)
    var showRobloxUser by BooleanBased("showRobloxUser", false)
}