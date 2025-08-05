package com.kednections.data.network.api

import com.kednections.data.network.dto.tag.response.TagResponse
import retrofit2.http.GET

interface TagApi {
    @GET("/tags/")
    suspend fun tags(
    ): Result<List<TagResponse>>
}
