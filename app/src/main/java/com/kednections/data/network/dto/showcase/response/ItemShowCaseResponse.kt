package com.kednections.data.network.dto.showcase.response

import com.google.gson.annotations.SerializedName

data class ItemShowCaseResponse(
    val id: String,
    @SerializedName("showcase_id")
    val showcaseId: String,
    val title: String,
    val description: String,
    @SerializedName("file_path")
    val filePath: String,
)