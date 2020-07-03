package com.github.daniilbug.auth

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface UserSessionProvider {
    @ExperimentalCoroutinesApi
    fun getAuthState(): StateFlow<UserAuthState>
}