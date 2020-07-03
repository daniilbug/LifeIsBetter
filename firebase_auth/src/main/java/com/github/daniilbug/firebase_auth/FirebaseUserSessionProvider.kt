package com.github.daniilbug.firebase_auth

import com.github.daniilbug.auth.UserAuthState
import com.github.daniilbug.auth.UserSessionProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FirebaseUserSessionProvider: UserSessionProvider {
    private val authState: MutableStateFlow<UserAuthState> = MutableStateFlow(UserAuthState.NotLoggedIn)

    init {
        GlobalScope.launch {
            Firebase.auth.state().collect { auth -> authState.value = auth.toUserAuthState() }
        }
    }

    override fun getAuthState(): StateFlow<UserAuthState> = authState

    private fun FirebaseAuth.toUserAuthState(): UserAuthState {
        val uid = uid ?: return UserAuthState.NotLoggedIn
        return UserAuthState.LoggedIn(FirebaseUserSession(uid))
    }
}
