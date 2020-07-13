package com.github.daniilbug.data

import kotlinx.coroutines.flow.Flow

interface MailsRepository {
    suspend fun sendMail(mail: Mail)
    suspend fun updateMailFeedback(mailId: String, feedback: Int)
    suspend fun getMyMails(userId: String, page: Any?, pageSize: Int): MailsResult
    suspend fun getMyMailsStatistic(userId: String): MailsStatistic

    fun getMailById(mailId: String): Flow<Mail>
}