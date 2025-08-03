package com.kednections.domain.models

data class RegUser(
    val username: String,
    val password: String,
    val specializations: List<Specialization>,
    val city: String,
    val description: String,
    val tags: List<Tag>,
    val communicationMethod: String,
    val photo: String?,
    val status: String?,
)