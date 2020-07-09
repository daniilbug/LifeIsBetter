package com.github.daniilbug.lifeisbetter.viewmodel.signup

sealed class SignUpStatus {
    class Error(val message: String): SignUpStatus()
}