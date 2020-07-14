package com.github.daniilbug.notifications

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class FirebaseNotificationSender : NotificationSender {
    private val client = OkHttpClient()
    private val jsonType = "application/json; charset=utf-8".toMediaType()

    override suspend fun sendNotification(userId: String, text: String) {
        val request = Request.Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=$SERVER_KEY")
            .url("https://fcm.googleapis.com/fcm/send")
            .post(
                """ {
                        "to" : "/topics/$userId",
                        "data" : {
                            "message" : "$text",
                        }
                    }
            """.trimIndent().toRequestBody(jsonType)
            )
            .build()
        client.newCall(request).enqueue(EmptyCallback())
    }
}