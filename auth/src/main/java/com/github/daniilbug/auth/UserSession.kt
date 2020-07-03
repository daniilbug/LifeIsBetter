package com.github.daniilbug.auth

interface UserSession {
    val userId: String
    suspend fun logOut()
}