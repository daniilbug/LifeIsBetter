package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserAuthState
import com.github.daniilbug.auth.UserSessionProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class MainInteractor(private val sessionProvider: UserSessionProvider) {
    enum class MainAuthState {
        LOGGED_IN, NOT_LOGGED_IN
    }

    fun isUserLoggedIn(): Flow<MainAuthState> {
        return sessionProvider.getAuthState().map { state -> stateToMainState(state) }
    }

    private fun stateToMainState(state: UserAuthState) = when(state) {
        is UserAuthState.LoggedIn -> MainAuthState.LOGGED_IN
        is UserAuthState.NotLoggedIn -> MainAuthState.NOT_LOGGED_IN
    }
}