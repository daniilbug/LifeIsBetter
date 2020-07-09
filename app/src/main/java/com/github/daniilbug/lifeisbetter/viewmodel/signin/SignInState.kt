package com.github.daniilbug.lifeisbetter.viewmodel.signin

sealed class SignInState {
    object Success: SignInState()
    object Default: SignInState()
    object Loading: SignInState()
}