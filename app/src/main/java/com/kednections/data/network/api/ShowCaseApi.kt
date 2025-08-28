package com.kednections.data.network.api

import com.kednections.data.network.dto.showcase.response.ItemShowCaseResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ShowCaseApi {
    @Multipart
    @POST("/work/")
    suspend fun upload(
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
    ): Response<ResponseBody>

    @GET("/work/all/{showcase_id}")
    suspend fun getAll(
        @Header("token") token: String,
        @Path("showcase_id") id: String,
    ): Result<List<ItemShowCaseResponse>>
}
