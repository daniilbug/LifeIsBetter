package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.exceptions.UserIsNotAuthorizedException
import com.github.daniilbug.data.Mail
import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.UserRepository
import com.github.daniilbug.notifications.NotificationSender
import com.github.daniilbug.notifications.NotificationSubscriptionManager
import java.util.*

class WriteMailInteractor(
    private val mailsRepository: MailsRepository,
    private val userRepository: UserRepository,
    private val notificationSender: NotificationSender,
    userSessionProvider: UserSessionProvider
) {
    private val session = userSessionProvider.getUserSession() ?: throw UserIsNotAuthorizedException()

    suspend fun sendMail(text: String) {
        val randomUser = userRepository.getRandomUser("")
        val mail = Mail(UUID.randomUUID().toString(), randomUser.id, session.userId, text, -1, System.currentTimeMillis())
        mailsRepository.sendMail(mail)
        notificationSender.sendNotification(randomUser.id, mail.content)
        notificationSender.sendNotification(session.userId, mail.content)
    }
}