package com.example.easychat.domain.model

import java.util.*

data class Message(
    var name: String = "User Name",
    val photoURL: String = "https://firebasestorage.googleapis.com/v0/b/light-chat-51369.appspot.com/o/profile_placeholder.png?alt=media&token=c33bb388-1f63-4615-818b-b21fddcd2491",
    val message: String = "",
    val timestamp: Date = Date()
)