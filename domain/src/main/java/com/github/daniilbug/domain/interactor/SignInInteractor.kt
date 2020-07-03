package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.AuthProvider

class SignInInteractor(private val authProvider: AuthProvider) {
    suspend fun signIn(email: String, password: String) {
        authProvider.signIn(email, password)
    }
}