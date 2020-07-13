package com.github.daniilbug.data

interface MailsRepository {
    suspend fun sendMail(mail: Mail)
    suspend fun getMyMails(page: Any?, pageSize: Int): MailsResult
}