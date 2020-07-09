package com.github.daniilbug.lifeisbetter.viewmodel.signin

sealed class SignInStatus {
    class Error(val message: String): SignInStatus()
}