package com.github.daniilbug.data

interface UserRepository {
    suspend fun addUser(user: User)
    suspend fun getRandomUser(exceptUserId: String): User
}