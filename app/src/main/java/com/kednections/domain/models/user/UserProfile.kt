package com.kednections.domain.models.user

import com.kednections.domain.models.NameOrNick

data class UserProfile(
    val username: String? = "",
    val fio: String? = "",
    val nick: String? = "",
    val photo: String? = null,
    val specializations: List<String>? = emptyList(),
    val city: String? = "",
    val description: String? = "",
    val tags: List<String>? = emptyList(),
    val communicationMethod: String? = "",
    val nameOrNick: NameOrNick,
    val status: String? = null,
)