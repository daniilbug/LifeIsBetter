package com.github.daniilbug.data

data class Mail(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val feedback: Int,
    val date: Long
)