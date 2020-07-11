package com.github.daniilbug.firebase_data

import com.github.daniilbug.data.GetMailsResult
import com.github.daniilbug.data.Mail
import com.github.daniilbug.data.MailsRepository

class FirebaseMailRepository: MailsRepository {
    private val mails = List(100) { index ->
        Mail("$index", "$index", "$index", "Message $index", index % 2 - 1, System.currentTimeMillis())
    }

    override suspend fun getMyMails(userId: String, page: Int, pageSize: Int): GetMailsResult {
        val nextPage = if (page * pageSize >= mails.size) -1 else page + 1
        return GetMailsResult(mails.subList((page - 1) * pageSize, page * pageSize), nextPage)
    }

    override suspend fun sendMail(mail: Mail) {

    }
}