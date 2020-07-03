package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.AuthProvider

class SignUpInteractor(private val authProvider: AuthProvider) {
    suspend fun signUp(email: String, password: String) {
        authProvider.signUp(email, password)
    }
}