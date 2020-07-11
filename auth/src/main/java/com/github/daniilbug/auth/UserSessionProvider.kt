package com.github.daniilbug.auth

interface UserSessionProvider {
    fun getUserSession(): UserSession?
}

fun UserSessionProvider.isUserLoggedIn() = getUserSession() != null