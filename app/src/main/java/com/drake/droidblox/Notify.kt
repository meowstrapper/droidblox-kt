package com.drake.droidblox

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.drake.droidblox.notifications.ChannelIds
import com.drake.droidblox.notifications.ChannelNames
import com.drake.logger.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Notification @Inject constructor(
    val logger: Logger,
    @ApplicationContext val context: Context
) {
    companion object {
        private const val TAG = "Notification"
    }

    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channel: NotificationChannel = NotificationChannel(
        ChannelIds.ROBLOX_SERVER_LOCATION,
        ChannelNames.ROBLOX_SERVER_LOCATION,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "The server's location whenever you join a game"
    }


    init {
        notificationManager.createNotificationChannel(channel)
    }

    fun requestPermission(activity: ComponentActivity, callback: (granted: Boolean) -> Unit = {}) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            logger.d(TAG, "SDK Version is lower than 13, ignoring")
            return
        }

        val requestPermissionLauncher = activity.registerForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            callback = callback
        )
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            logger.d(TAG, "Permission is already granted")
        } else {
            logger.d(TAG, "Requesting permission")
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun notify(title: String, description: String) = notificationManager.notify(
        0,
        NotificationCompat.Builder(context, ChannelIds.ROBLOX_SERVER_LOCATION)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .build()
    )
}