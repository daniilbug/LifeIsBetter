package com.github.daniilbug.firebase_auth

import com.github.daniilbug.auth.UserSession
import com.github.daniilbug.auth.UserSessionProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseUserSessionProvider: UserSessionProvider {
    override fun getUserSession(): UserSession? {
        val userId = Firebase.auth.currentUser?.uid ?: return null
        return FirebaseUserSession(userId)
    }
}
