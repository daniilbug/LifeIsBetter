package com.github.daniilbug.notifications

interface NotificationSubscriptionManager {
    suspend fun subscribe(userId: String)
    suspend fun unsubscribe(userId: String)
}