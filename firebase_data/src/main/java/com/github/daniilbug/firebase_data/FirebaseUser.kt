package com.github.daniilbug.firebase_data

import com.github.daniilbug.data.User

class FirebaseUser(
    val id: String = "",
    val email: String = ""
)

fun FirebaseUser.toUser() = User(id, email)
fun User.toFirebase() = FirebaseUser(id, email)