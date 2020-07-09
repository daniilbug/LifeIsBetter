package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.UserAuthState
import com.github.daniilbug.auth.UserSessionProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInInteractor(private val authProvider: AuthProvider, private val sessionProvider: UserSessionProvider) {
    fun isUserLoggedIn() = sessionProvider.isUserLoggedIn()

    suspend fun signIn(email: String, password: String) {
        authProvider.signIn(email, password)
    }
}