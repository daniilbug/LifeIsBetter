package com.github.daniilbug.domain.interactor

import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.MailsResult

class MailListInteractor(private val mailListRepository: MailsRepository) {

    suspend fun getMailsByPage(page: Any?, pageSize: Int): MailsResult {
        return mailListRepository.getMyMails(page, pageSize)
    }
}