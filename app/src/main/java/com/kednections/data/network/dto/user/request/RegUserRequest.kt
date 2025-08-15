package com.kednections.data.network.dto.user.request

import com.google.gson.annotations.SerializedName

data class RegUserRequest(
    val email: String,
    val username: String,
    val password: String,
    val description: String,
    @SerializedName("name_display")
    val nameDisplay: String,
    val city: String,
    val tags: List<String>,
    @SerializedName("communication_method")
    val communicationMethod: String,
    val specialization: List<String>,
    val nickname: String,
    val photo: String? = null,
    val status: String? = null,
)