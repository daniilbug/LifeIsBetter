package com.github.daniilbug.firebase_data

import com.github.daniilbug.data.User
import com.github.daniilbug.data.UserRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseUserRepository : UserRepository {
    private val usersDb = Firebase.firestore.collection("users")

    override suspend fun addUser(user: User) {
        usersDb.addValue(user.toFirebase())
    }

    override suspend fun getRandomUser(exceptUserId: String): User {
        val users = usersDb.loadList<FirebaseUser>()
        var randomUser: FirebaseUser
        do {
            randomUser = users.random()
        } while (randomUser.id == exceptUserId)
        return randomUser.toUser()
    }
}