package com.github.daniilbug.notifications

import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun FirebaseMessaging.subscribe(topic: String) = suspendCoroutine<Unit> { cont ->
    subscribeToTopic(topic).addOnSuccessListener {
        cont.resumeWith(Result.success(Unit))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}

suspend fun FirebaseMessaging.unsubscribe(topic: String) = suspendCoroutine<Unit> { cont ->
    unsubscribeFromTopic(topic).addOnSuccessListener {
        cont.resumeWith(Result.success(Unit))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}