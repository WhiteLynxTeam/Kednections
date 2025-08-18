package com.kednections.data.network.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val email: String,
    val username: String,
    val nickname: String,
    val photo: String,
    val specializations: List<String>,
    val city: String,
    val description: String,
    val tags: List<String>,
    @SerializedName("communication_method")
    val communicationMethod: String,
    val status: String,
    val showcase: String,
    @SerializedName("name_display")
    val nameOrNick: String,
)