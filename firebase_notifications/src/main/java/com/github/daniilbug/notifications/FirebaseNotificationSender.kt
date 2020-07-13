package com.github.daniilbug.notifications

import android.app.Notification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class FirebaseNotificationSender : NotificationSender {
    private val client = OkHttpClient()
    private val jsonType = "application/json; charset=utf-8".toMediaType()

    override suspend fun sendNotification(userId: String, text: String) {
        val request = Request.Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "bearer $SERVER_KEY")
            .url("https://fcm.googleapis.com/v1/projects/lifeisbetter-b3423/messages:send\n")
            .post(
                """
                  "message":{
                    "topic" : "$userId"
                    "notification": {
                        "body" : "$text",
                        "title" : "$text",
                    }
                }
            """.trimIndent().toRequestBody(jsonType)
            )
            .build()
        client.newCall(request)
    }
}