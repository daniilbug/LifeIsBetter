package com.github.daniilbug.data

import kotlinx.coroutines.flow.Flow

interface MailsRepository {
    suspend fun sendMail(mail: Mail)
    fun getMyMails(userId: String): Flow<List<Mail>>
}