package com.kednections.domain.models.feed

data class Feed(
    val images: List<ImageDetail>,
    val city: String,
    val avatar: Int,
    val name: String,
    val specialization: String,
    var isOnline: Boolean = false,
    //var isSubscribe: Boolean = false
)

