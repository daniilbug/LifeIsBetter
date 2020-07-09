package com.github.daniilbug.lifeisbetter.viewmodel.signin

sealed class SignInEvent {
    class SignIn(val email: String, val password: String): SignInEvent()
}