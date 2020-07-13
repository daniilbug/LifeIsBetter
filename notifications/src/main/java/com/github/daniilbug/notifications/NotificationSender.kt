package com.github.daniilbug.notifications

interface NotificationSender {
    suspend fun sendNotification(userId: String, text: String)
}