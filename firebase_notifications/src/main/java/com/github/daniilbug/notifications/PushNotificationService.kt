package com.github.daniilbug.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


    companion object {
        const val CHANNEL_ID = "com.github.daniilbug.lifeisbetter.mails"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_title),
                getString(R.string.notification_channel_description)
            )
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleMessage(remoteMessage)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        val body = remoteMessage.data["message"] ?: return

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.new_message))
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_letter_closed)
            .setChannelId(CHANNEL_ID)
            .build()
        notificationManager.notify(body.hashCode(), notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        id: String,
        name: String,
        description: String
    ): NotificationChannel {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager.createNotificationChannel(channel)
        return channel
    }
}