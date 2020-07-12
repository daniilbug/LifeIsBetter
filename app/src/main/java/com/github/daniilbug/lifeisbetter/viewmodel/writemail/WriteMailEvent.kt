package com.github.daniilbug.lifeisbetter.viewmodel.writemail

sealed class WriteMailEvent {
    class SendMail(val text: String): WriteMailEvent()
}