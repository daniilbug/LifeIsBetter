package com.github.daniilbug.firebase_data

import com.github.daniilbug.data.MailsResult
import com.github.daniilbug.data.Mail
import com.github.daniilbug.data.MailsRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FirebaseMailRepository : MailsRepository {
    private val mailsDb = Firebase.database.getReference("mails")
    private val usersDb = Firebase.database.getReference("users")

    override suspend fun getMyMailsFirstPage(userId: String, pageSize: Int): MailsResult {
        return getMails(userId, 0, pageSize)
    }

    override suspend fun getMyMailsByPage(userId: String, pageId: Any, pageSize: Int): MailsResult {
        val lastMailId = pageId as? Long ?: error("pageId must be String for FirebaseMailRepository")
        return getMails(userId, lastMailId, pageSize)
    }

    override suspend fun sendMail(mail: Mail) {
        if (mail.content.isBlank()) error("Mail content must not be blank")
        val userIds = usersDb.getKeys()
        val receiverId = userIds.random()
        mailsDb.child(receiverId).child(mail.id).setValue(mail.toFirebase())
    }

    private suspend fun getMails(userId: String, pageId: Long, pageSize: Int): MailsResult {
        val loadedResult = mailsDb.child(userId)
            .orderByChild("date")
            .endAt(pageId.toDouble())
            .limitToFirst(pageSize)
            .getListWithKeys<FirebaseMail>()
        if (loadedResult.isEmpty()) return MailsResult(listOf(), null)
        val mails = loadedResult.map { mailWithId -> mailWithId.data.toMail(mailWithId.key) }
        val nextPage = loadedResult.last().data.date - 1
        return MailsResult(mails, nextPage)
    }
}