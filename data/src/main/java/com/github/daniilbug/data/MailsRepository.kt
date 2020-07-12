package com.github.daniilbug.data

interface MailsRepository {
    suspend fun sendMail(mail: Mail)
    suspend fun getMyMailsFirstPage(userId: String, pageSize: Int): MailsResult
    suspend fun getMyMailsByPage(userId: String, pageId: Any, pageSize: Int): MailsResult
}