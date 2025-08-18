package com.kednections.data.network.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FileApi {
    @GET("/file/{uuid}")
    suspend fun getFile(
        @Header("token") token: String,
        @Path("uuid") uuid: String,
    ): Response<ResponseBody>
}
