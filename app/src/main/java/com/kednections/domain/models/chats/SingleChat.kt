package com.kednections.domain.models.chats

data class SingleChat(
    val message: String,
    val date: String,
    val isMatch: Boolean = false,
    val isSent: Boolean = false
)
