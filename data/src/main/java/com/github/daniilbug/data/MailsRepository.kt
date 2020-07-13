package com.github.daniilbug.data

import kotlinx.coroutines.flow.Flow

interface MailsRepository {
    suspend fun sendMail(mail: Mail)
    suspend fun updateMailFeedback(mailId: String, feedback: Int)
    suspend fun getMyMails(page: Any?, pageSize: Int): MailsResult

    fun getMailById(mailId: String): Flow<Mail>
}