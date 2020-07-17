package com.github.daniilbug.notifications

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun FirebaseMessaging.subscribe(topic: String) = suspendCancellableCoroutine<Unit> { cont ->
    subscribeToTopic(topic).addOnSuccessListener {
        cont.resumeWith(Result.success(Unit))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}

suspend fun FirebaseMessaging.unsubscribe(topic: String) = suspendCancellableCoroutine<Unit> { cont ->
    unsubscribeFromTopic(topic).addOnSuccessListener {
        cont.resumeWith(Result.success(Unit))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}