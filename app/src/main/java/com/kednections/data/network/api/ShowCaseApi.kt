package com.kednections.data.network.api

import com.kednections.data.network.dto.showcase.response.ItemShowCaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ShowCaseApi {
    @Multipart
    @POST("/work/")
    suspend fun upload(
        @Header("token") token: String,
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part partFile: MultipartBody.Part?,
        ): Response<ResponseBody>

    // для удаления элемента showcase
    @DELETE("/work/{item_id}")
    suspend fun delete(
        @Header("token") token: String,
        @Path("item_id") itemId: String,
    ): Response<ResponseBody>

    // для обновления элемента showcase (PATCH запрос)
    @Multipart
    @PATCH("/work/{item_id}")
    suspend fun update(
        @Header("token") token: String,
        @Path("item_id") itemId: String, // ID элемента для обновления
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part partFile: MultipartBody.Part?,
    ): Response<ResponseBody>

    @GET("/work/all/{showcase_id}")
    suspend fun getAll(
        @Header("token") token: String,
        @Path("showcase_id") id: String,
    ): Result<List<ItemShowCaseResponse>>
}
