package com.kednections.data.network.dto.showcase.request

data class PhotoFileRequest(
    val title: String? = null,
    val description: String = "",
    val file: String,
)