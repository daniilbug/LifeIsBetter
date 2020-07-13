package com.github.daniilbug.lifeisbetter.viewmodel.maildetails

sealed class MailDetailsStatus {
    class Error(val message: String): MailDetailsStatus()
}