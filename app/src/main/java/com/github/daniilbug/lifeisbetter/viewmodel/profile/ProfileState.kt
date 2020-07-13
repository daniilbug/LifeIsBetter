package com.github.daniilbug.lifeisbetter.viewmodel.profile

sealed class ProfileState {
    object LogOut: ProfileState()
    class Statistic(val mailsAmount: Int, val goodMailsAmount: Int): ProfileState()
}