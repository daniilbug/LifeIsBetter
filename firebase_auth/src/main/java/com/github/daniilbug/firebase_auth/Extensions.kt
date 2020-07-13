package com.github.daniilbug.firebase_auth

import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun FirebaseAuth.signUp(email: String, password: String) =
    suspendCoroutine<String> { cont ->
        val task = createUserWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            cont.resumeWith(
                Result.success(
                    it.user?.uid ?: error("User is not registered")
                )
            )
        }
        task.addOnFailureListener { ex -> cont.resumeWithException(ex) }
    }

suspend fun FirebaseAuth.signIn(email: String, password: String) =
    suspendCoroutine<String> { cont ->
        val task = signInWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            cont.resumeWith(
                Result.success(
                    it.user?.uid ?: error("User is not registered")
                )
            )
        }
        task.addOnFailureListener { ex -> cont.resumeWithException(ex) }
    }