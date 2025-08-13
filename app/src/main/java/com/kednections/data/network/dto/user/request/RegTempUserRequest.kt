package com.kednections.data.network.dto.user.request

data class RegTempUserRequest(
    val email: String,
    val username: String,
    val password: String,
    val description: String,
    val city: String,
    val tags: List<String>,
    val communication_method: String,
    val specialization: List<String>,
    val nickname: String,
    val status: String?  = null,
    val photo: String?  = null,
)