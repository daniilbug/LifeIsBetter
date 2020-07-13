package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.exceptions.ConfirmPasswordIsWrongException
import com.github.daniilbug.data.User
import com.github.daniilbug.data.UserRepository

class SignUpInteractor(private val authProvider: AuthProvider, private val userRepository: UserRepository) {
    suspend fun signUp(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword)
            throw ConfirmPasswordIsWrongException()
        val authUser = authProvider.signUp(email, password)
        userRepository.addUser(User(authUser.id, authUser.email))
    }
}