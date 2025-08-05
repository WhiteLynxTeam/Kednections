package com.kednections.data.network.api

import com.kednections.data.network.dto.geo.response.CityResponse
import retrofit2.http.GET

interface GeoApi {
    @GET("/cities/")
    suspend fun cities(
    ): Result<List<CityResponse>>
}
