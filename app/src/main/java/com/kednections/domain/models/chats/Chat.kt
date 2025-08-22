package com.kednections.domain.models.chats

data class Chat(
    val avatar: Int,
    val name: String,
    val message: String,
    val time: String,
    val count: Int = 0,
    val matchIndicator: Boolean = false
)
