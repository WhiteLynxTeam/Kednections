package com.kednections.data.network.dto.user.request

import com.kednections.domain.models.Specialization
import com.kednections.domain.models.Tag

data class RegUserRequest(
    val username: String,
    val password: String,
    val fio: String,
    val nick: String,
    val specializations: List<Specialization>,
    val city: String,
    val description: String,
    val tags: List<Tag>,
    val communicationMethod: String,
    val photo: String?  = null,
    val status: String?  = null,
)
