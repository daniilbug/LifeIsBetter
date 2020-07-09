package com.github.daniilbug.domain.interactor

import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.ConfirmPasswordIsWrongException

class SignUpInteractor(private val authProvider: AuthProvider) {
    suspend fun signUp(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword)
            throw ConfirmPasswordIsWrongException()
        authProvider.signUp(email, password)
    }
}