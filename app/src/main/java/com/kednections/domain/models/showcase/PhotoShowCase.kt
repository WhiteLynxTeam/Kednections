package com.kednections.domain.models.showcase

data class PhotoShowCase(
    val id: String,
    val title: String? = null,
    val description: String  = "",
    val photo: String,
)