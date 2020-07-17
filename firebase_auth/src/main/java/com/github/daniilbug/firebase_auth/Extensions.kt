package com.github.daniilbug.firebase_auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resumeWithException

suspend fun FirebaseAuth.signUp(email: String, password: String) =
    suspendCancellableCoroutine<String> { cont ->
        val task = createUserWithEmailAndPassword(email, password)
        addSuccessFailureCallbacks(task, cont)
    }

suspend fun FirebaseAuth.signIn(email: String, password: String) =
    suspendCancellableCoroutine<String> { cont ->
        val task = signInWithEmailAndPassword(email, password)
        addSuccessFailureCallbacks(task, cont)
    }

private fun addSuccessFailureCallbacks(
    task: Task<AuthResult>,
    cont: Continuation<String>
) {
    task.addOnSuccessListener {
        cont.resumeWith(
            Result.success(
                it.user?.uid ?: error("User is not registered")
            )
        )
    }
    task.addOnFailureListener { ex -> cont.resumeWithException(ex) }
}