package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.exceptions.UserIsNotAuthorizedException
import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.MailsResult

class MailListInteractor(private val mailListRepository: MailsRepository, private val sessionProvider: UserSessionProvider) {

    suspend fun getMailsByPage(page: Any?, pageSize: Int): MailsResult {
        val session = sessionProvider.getUserSession() ?: throw UserIsNotAuthorizedException()
        return mailListRepository.getMyMails(session.userId, page, pageSize)
    }
}