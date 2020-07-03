package com.github.daniilbug.firebase_auth

import com.github.daniilbug.auth.UserSession
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseUserSession(override val userId: String): UserSession {
    override suspend fun logOut() {
        Firebase.auth.signOut()
    }
}