package com.github.daniilbug.lifeisbetter.viewmodel.writemail

sealed class WriteMailStatus {
    object Success: WriteMailStatus()
    class Error(val message: String): WriteMailStatus()
}