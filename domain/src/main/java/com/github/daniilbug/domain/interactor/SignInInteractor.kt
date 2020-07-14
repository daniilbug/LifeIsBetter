package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.isUserLoggedIn
import com.github.daniilbug.notifications.NotificationSubscriptionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInInteractor(
    private val authProvider: AuthProvider,
    private val sessionProvider: UserSessionProvider,
    private val notificationSubscriptionManager: NotificationSubscriptionManager
) {
    fun isUserLoggedIn() = sessionProvider.isUserLoggedIn()

    suspend fun signIn(email: String, password: String) {
        val authUser = authProvider.signIn(email, password)
        notificationSubscriptionManager.subscribe(authUser.id)
    }
}