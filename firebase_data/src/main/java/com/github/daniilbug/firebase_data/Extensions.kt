package com.github.daniilbug.firebase_data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend inline fun<reified T: Any> Query.loadList() = suspendCoroutine<List<T>> { cont ->
    get().addOnSuccessListener { snapshot ->
        val objects = snapshot.toObjects<T>()
        cont.resumeWith(Result.success(objects))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}

suspend fun CollectionReference.addValue(any: Any) = suspendCoroutine<Unit> { cont ->
    add(any).addOnSuccessListener {
        cont.resumeWith(Result.success(Unit))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}
