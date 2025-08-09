package com.kednections.data.network.dto.user.request

data class RegUserRequest(
    val email: String,
    val password: String,
    val username: String,
    val nickname: String,
    val specialization: List<String>,
    val city: String,
    val description: String,
    val tags: List<String>,
    val communication_method: String,
    val photo: String?  = null,
    val status: String?  = null,
)