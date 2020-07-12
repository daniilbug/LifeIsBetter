package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.exceptions.UserIsNotAuthorizedException
import com.github.daniilbug.data.Mail
import com.github.daniilbug.data.MailsResult
import com.github.daniilbug.data.MailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class MailListInteractor(private val mailListRepository: MailsRepository, sessionProvider: UserSessionProvider) {
    private val session = sessionProvider.getUserSession() ?: throw UserIsNotAuthorizedException()

    suspend fun getMailsFirstPage(pageSize: Int): MailsResult {
        return mailListRepository.getMyMailsFirstPage(session.userId, pageSize)
    }

    suspend fun getMailsByPage(pageId: Any, pageSize: Int): MailsResult {
        return mailListRepository.getMyMailsByPage(session.userId, pageId, pageSize)
    }
}