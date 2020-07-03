package com.github.daniilbug.auth

sealed class UserAuthState {
    class LoggedIn(val session: UserSession): UserAuthState()
    object NotLoggedIn: UserAuthState()
}