package com.github.daniilbug.firebase_data

import com.github.daniilbug.data.Mail
import java.util.*

data class FirebaseMail(
    val id: String = UUID.randomUUID().toString(),
    val senderId: String = "",
    val content: String = "",
    val feedback: Int = -1,
    val date: Long = System.currentTimeMillis(),
    val receiverId: String = ""
)

fun FirebaseMail.toMail() = Mail(id, receiverId, senderId, content, feedback, -date)
fun Mail.toFirebase(): FirebaseMail = FirebaseMail(id, senderId, content, feedback, -date, receiverId)