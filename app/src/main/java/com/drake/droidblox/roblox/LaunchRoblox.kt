package com.drake.droidblox.roblox

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun launchRoblox(context: Context, placeId: Long? = null, jobId: String? = null) {
    val intent = Intent().apply {
        component = ComponentName("com.roblox.client", "com.roblox.client.ActivityProtocolLaunch")
        data = "roblox://experiences/start".toUri()
            .buildUpon()
            .apply {
                placeId?.let {
                    appendQueryParameter("placeId", it.toString())
                }
                jobId?.let {
                    appendQueryParameter("gameInstanceId", it)
                }
            }
            .build()
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}