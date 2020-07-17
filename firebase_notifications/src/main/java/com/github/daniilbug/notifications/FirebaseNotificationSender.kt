package com.github.daniilbug.notifications

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
            .addHeader("Authorization", "key=$SERVER_KEY")
            .url("https://fcm.googleapis.com/fcm/send")
            .post(makeJson(userId, text).toRequestBody(jsonType))
            .build()
        client.newCall(request).enqueue(EmptyResponseCallback())
    }

    private fun makeJson(userId: String, text: String): String {
        return """ {
                        "to" : "/topics/$userId",
                        "data" : {
                            "message" : "$text",
                        }
                    }
            """.trimIndent()
    }
}