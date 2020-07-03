package com.github.daniilbug.lifeisbetter.viewmodel

import java.util.*

data class MailView(
    val id: String,
    val text: String,
    val date: Date,
    val isOpened: Boolean
)