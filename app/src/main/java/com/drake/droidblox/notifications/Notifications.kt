package com.drake.droidblox.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.drake.logger.Logger
import com.drake.droidblox.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Notifications @Inject constructor(
  private val logger: Logger,
  @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "Notifications"
    }

    private lateinit var notificationManager: NotificationManager
    private lateinit var serverLocationChannel: NotificationChannel

    private fun initNotifications() {
        logger.d(TAG, "Initializing notifications")

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        serverLocationChannel = NotificationChannel(
            "roblox_server_location",
            "Server Location",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            this.description = "The server's location whenever you join a game"
        }
        notificationManager.createNotificationChannel(serverLocationChannel)
    }

    fun notify(title: String, subtitle: String) {
        if (!::notificationManager.isInitialized) {
            initNotifications()
        }
        logger.d(TAG, "Building notification for title $title subtitle $subtitle")
        val builder = NotificationCompat.Builder(context, "roblox_server_location")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(subtitle)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
        logger.d(TAG, "Notifying")
        notificationManager.notify(0, builder.build())
    }
}