package com.github.daniilbug.auth

interface AuthProvider {
    suspend fun signIn(email: String, password: String): AuthUser
    suspend fun signUp(email: String, password: String): AuthUser
}