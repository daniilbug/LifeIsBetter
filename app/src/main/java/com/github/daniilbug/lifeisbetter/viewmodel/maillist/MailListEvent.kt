package com.github.daniilbug.lifeisbetter.viewmodel.maillist

sealed class MailListEvent {
    object Reload: MailListEvent()
}