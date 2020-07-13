package com.github.daniilbug.auth

interface AuthProvider {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String): AuthUser
}