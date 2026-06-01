package com.drake.droidblox

import android.app.Application
import android.content.Context
import android.util.Log

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DBApplication : Application() {
    companion object {
        private const val TAG = "DBApplication"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d(TAG, "attachBaseContext()")
    }
}