package com.drake.droidblox

import android.app.Application
import android.content.Context
import android.util.Log
import com.drake.droidblox.sharedprefs.PlaySessionsManager
import com.drake.droidblox.sharedprefs.SettingsManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DBApplication : Application() {
    companion object {
        private const val TAG = "DBApplication"
        lateinit var instance: DBApplication
            private set
    }

    val settingsManager: SettingsManager by lazy { SettingsManager(applicationContext) }
    val playSessionsManager: PlaySessionsManager by lazy { PlaySessionsManager(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")

        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d(TAG, "attachBaseContext()")
    }
}