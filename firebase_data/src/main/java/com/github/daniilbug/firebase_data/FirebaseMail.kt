package com.github.daniilbug.firebase_data

import com.github.daniilbug.data.Mail
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseMail(
    val senderId: String = "",
    val content: String = "",
    val feedback: Int = -1,
    val date: Long = System.currentTimeMillis()
)

fun FirebaseMail.toMail(id: String) = Mail(id, senderId, content, feedback, -date)
fun Mail.toFirebase(): FirebaseMail = FirebaseMail(senderId, content, feedback, -date)