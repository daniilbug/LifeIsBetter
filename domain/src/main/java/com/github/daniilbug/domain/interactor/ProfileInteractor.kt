package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.exceptions.UserIsNotAuthorizedException
import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.MailsStatistic

class ProfileInteractor(
    private val mailsRepository: MailsRepository,
    userSessionProvider: UserSessionProvider
) {
    private val session = userSessionProvider.getUserSession() ?: throw UserIsNotAuthorizedException()

    suspend fun logOut() {
        session.logOut()
    }

    suspend fun getStatistic(): MailsStatistic {
         return mailsRepository.getMyMailsStatistic(session.userId)
    }
}