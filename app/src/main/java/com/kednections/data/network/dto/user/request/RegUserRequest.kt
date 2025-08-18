package com.kednections.data.network.dto.user.request

import com.google.gson.annotations.SerializedName
import com.kednections.domain.models.NameOrNick

data class RegUserRequest(
    val email: String,
    val username: String,
    val password: String,
    val description: String,
    @SerializedName("name_display")
    val nameDisplay: NameOrNick,
    val city: String,
    val tags: List<String>,
    @SerializedName("communication_method")
    val communicationMethod: String,
    val specialization: List<String>,
    val nickname: String,
    val photo: String? = null,
    val status: String? = null,
)