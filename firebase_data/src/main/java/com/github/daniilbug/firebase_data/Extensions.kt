package com.github.daniilbug.firebase_data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

suspend fun DocumentReference.setValue(any: Any) = suspendCoroutine<Unit> { cont ->
    set(any).addOnSuccessListener {
        cont.resumeWith(Result.success(Unit))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}

suspend fun DocumentReference.updateValue(fieldName: String, any: Any) = suspendCoroutine<Unit> { cont ->
    update(fieldName, any).addOnSuccessListener {
        cont.resumeWith(Result.success(Unit))
    }.addOnFailureListener { ex ->
        cont.resumeWithException(ex)
    }
}

@ExperimentalCoroutinesApi
inline fun <reified T: Any> DocumentReference.flow(): Flow<T> = callbackFlow<T> {
    val disposable: ListenerRegistration = addSnapshotListener { value, error ->
        if (error != null)
            close(error)
        val data = value?.toObject(T::class.java) ?: error("Error in parsing ${T::class.simpleName} from firebase cloud storage")
        sendBlocking(data)
    }

    awaitClose { disposable.remove() }
}
