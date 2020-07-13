package com.github.daniilbug.firebase_data

import com.github.daniilbug.data.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class FirebaseMailRepository : MailsRepository {
    private val mailsDb = Firebase.firestore.collection("mails")

    override suspend fun getMyMails(userId: String, page: Any?, pageSize: Int): MailsResult {
        if (page is Long?) {
            return getMails(userId, page, pageSize)
        }
        error("page must be Long or null for ${FirebaseMailRepository::class.simpleName}")
    }

    override suspend fun sendMail(mail: Mail) {
        if (mail.content.isBlank()) throw BlankMailException()
        mailsDb.document(mail.id).setValue(mail.toFirebase())
    }

    override suspend fun updateMailFeedback(mailId: String, feedback: Int) {
        mailsDb.document(mailId).updateValue("feedback", feedback)
    }

    override suspend fun getMyMailsStatistic(userId: String): MailsStatistic {
        val myMails = mailsDb.whereEqualTo("senderId", userId)
            .loadList<FirebaseMail>()
        val goodAmount = myMails.count { mail -> mail.feedback == 2 }
        return MailsStatistic(myMails.size, goodAmount)
    }

    override fun getMailById(mailId: String): Flow<Mail> {
        return mailsDb.document(mailId).flow<FirebaseMail>().map { fbMail -> fbMail.toMail() }
    }

    private suspend fun getMails(userId: String, pageId: Long?, pageSize: Int): MailsResult {
        val fbMails = mailsDb.whereEqualTo("receiverId", userId)
            .orderBy("date")
            .limit(pageSize.toLong())
            .startAfter(pageId)
            .loadList<FirebaseMail>()
        if (fbMails.isEmpty()) return MailsResult(emptyList(), null)
        val mails = fbMails.map(FirebaseMail::toMail)
        return MailsResult(mails, fbMails.last().date)
    }
}