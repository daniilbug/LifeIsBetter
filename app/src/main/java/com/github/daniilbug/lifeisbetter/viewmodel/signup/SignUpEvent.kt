package com.github.daniilbug.lifeisbetter.viewmodel.signup

sealed class SignUpEvent {
    class SignUp(val email: String, val password: String, val confirmPassword: String): SignUpEvent()
}