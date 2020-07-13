package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.auth.exceptions.UserIsNotAuthorizedException
import com.github.daniilbug.data.Mail
import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.UserRepository
import java.util.*

class WriteMailInteractor(
    private val mailsRepository: MailsRepository,
    private val userRepository: UserRepository,
    userSessionProvider: UserSessionProvider
) {
    private val session = userSessionProvider.getUserSession() ?: throw UserIsNotAuthorizedException()

    suspend fun sendMail(text: String) {
        val randomUser = userRepository.getRandomUser("")
        val mail = Mail(UUID.randomUUID().toString(), randomUser.id, session.userId, text, -1, System.currentTimeMillis())
        mailsRepository.sendMail(mail)
    }
}