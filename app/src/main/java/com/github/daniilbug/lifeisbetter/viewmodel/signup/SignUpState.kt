package com.github.daniilbug.lifeisbetter.viewmodel.signup

sealed class SignUpState {
    object Success: SignUpState()
    object Default: SignUpState()
    object Loading: SignUpState()
}