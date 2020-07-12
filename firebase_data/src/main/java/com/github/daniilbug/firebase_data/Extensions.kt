package com.github.daniilbug.firebase_data

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

data class WithId<T : Any>(val key: String, val data: T)

suspend inline fun <reified T : Any> Query.getListWithKeys() =
    suspendCoroutine<List<WithId<T>>> { cont ->
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                cont.resumeWithException(error.toException())
                removeEventListener(this)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.map {
                    val key = it.key
                    val value = it.getValue<T>()
                    checkNotNull(key)
                    checkNotNull(value)
                    WithId(key, value)
                }
                cont.resumeWith(Result.success(data))
                removeEventListener(this)
            }
        }
        addListenerForSingleValueEvent(listener)
    }

suspend fun DatabaseReference.getKeys() = suspendCoroutine<List<String>> { cont ->
    val listener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            cont.resumeWithException(error.toException())
            removeEventListener(this)
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            val data = snapshot.children.map {
                it.key ?: error("Child of snapshot was not parsed the right way")
            }
            cont.resumeWith(Result.success(data))
            removeEventListener(this)
        }
    }
    addValueEventListener(listener)
}

