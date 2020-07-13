package com.github.daniilbug.notifications

import com.google.firebase.messaging.FirebaseMessaging

class FirebaseNotificationSubscriptionManager: NotificationSubscriptionManager {

    override suspend fun subscribe(userId: String) {
        FirebaseMessaging.getInstance().subscribe(userId)
    }

    override suspend fun unsubscribe(userId: String) {
        FirebaseMessaging.getInstance().unsubscribe(userId)
    }
}