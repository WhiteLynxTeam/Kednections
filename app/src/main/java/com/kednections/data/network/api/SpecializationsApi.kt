package com.kednections.data.network.api

import com.kednections.data.network.dto.specialization.response.SpecializationResponse
import retrofit2.http.GET

interface SpecializationsApi {
    @GET("/specializations/")
    suspend fun specializations(
    ): Result<List<SpecializationResponse>>
}
