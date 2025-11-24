package com.drake.droidblox.backend

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.drake.droidblox.R

class NotificationHandler(
    val activity: Activity,
    val title: String
) {
    private val TAG = "DBNotification"

    private val notifService = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val notifChannel = NotificationChannel(
        "com.drake.droidblox",
        title,
        NotificationManager.IMPORTANCE_HIGH
    )

    init {
        Log.d(TAG, "Setting notification channel")
        notifService.createNotificationChannel(notifChannel)
    }

    fun notify(text: String) {
        Log.d(TAG, "Constructing notification with subtitle $text")
        val notification = Notification.Builder(activity, "com.drake.droidblox")
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_notification)

        Log.d(TAG, "Sending out notification")
        notifService.notify(0, notification.build())
    }
}