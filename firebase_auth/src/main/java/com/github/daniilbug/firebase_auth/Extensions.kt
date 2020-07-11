package com.github.daniilbug.firebase_auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun FirebaseAuth.signUp(email: String, password: String) = suspendCoroutine<Unit> { cont ->
    val task = createUserWithEmailAndPassword(email, password)
    task.addOnSuccessListener { cont.resumeWith(Result.success(Unit)) }
    task.addOnFailureListener { ex -> cont.resumeWithException(ex) }
}

suspend fun FirebaseAuth.signIn(email: String, password: String) = suspendCoroutine<Unit> { cont ->
    val task = signInWithEmailAndPassword(email, password)
    task.addOnSuccessListener { cont.resumeWith(Result.success(Unit)) }
    task.addOnFailureListener { ex -> cont.resumeWithException(ex) }
}