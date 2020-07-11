package com.github.daniilbug.data

interface MailsRepository {
    suspend fun sendMail(mail: Mail)
    suspend fun getMyMails(userId: String, page: Int, pageSize: Int): GetMailsResult
}