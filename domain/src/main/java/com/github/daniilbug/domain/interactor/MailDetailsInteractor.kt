package com.github.daniilbug.domain.interactor

import com.github.daniilbug.data.MailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class MailDetailsInteractor(private val mailsRepository: MailsRepository) {
    suspend fun setFeedBack(mailId: String, feedBack: Int) {
        mailsRepository.updateMailFeedback(mailId, feedBack)
    }

    fun getFeedBack(mailId: String): Flow<Int> {
        return mailsRepository.getMailById(mailId).map { mail -> mail.feedback }
    }
}