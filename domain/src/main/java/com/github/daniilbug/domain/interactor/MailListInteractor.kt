package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.exceptions.UserIsNotAuthorizedException
import com.github.daniilbug.data.GetMailsResult
import com.github.daniilbug.data.MailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MailListInteractor(private val mailListRepository: MailsRepository, private val sessionProvider: UserSessionProvider) {
    suspend fun getMails(page: Int, pageSize: Int): GetMailsResult {
        val session = sessionProvider.getUserSession() ?: throw UserIsNotAuthorizedException()
        return mailListRepository.getMyMails(session.userId, page, pageSize)
    }
}