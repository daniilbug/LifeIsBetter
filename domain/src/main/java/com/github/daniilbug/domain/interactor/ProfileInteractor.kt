package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.exceptions.UserIsNotAuthorizedException
import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.MailsStatistic
import com.github.daniilbug.notifications.NotificationSubscriptionManager

class ProfileInteractor(
    private val mailsRepository: MailsRepository,
    private val notificationSubscriptionManager: NotificationSubscriptionManager,
    private val userSessionProvider: UserSessionProvider
) {

    private val session get() = userSessionProvider.getUserSession() ?: throw UserIsNotAuthorizedException()

    suspend fun logOut() {
        session.logOut()
        notificationSubscriptionManager.unsubscribe(session.userId)
    }

    suspend fun getStatistic(): MailsStatistic {
         return mailsRepository.getMyMailsStatistic(session.userId)
    }
}