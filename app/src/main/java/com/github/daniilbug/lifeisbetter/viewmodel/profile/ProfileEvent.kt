package com.github.daniilbug.lifeisbetter.viewmodel.profile

sealed class ProfileEvent {
    object LogOut: ProfileEvent()
    object Refresh : ProfileEvent()
}