package com.kednections.data.network.api

import com.kednections.data.network.dto.token.response.AuthTokenResponse
import com.kednections.data.network.dto.user.request.LoginUserRequest
import com.kednections.data.network.dto.user.request.RegUserRequest
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface UserApi {
    @POST("/auth/login")
    suspend fun login(
        @Body loginUserRequest: LoginUserRequest
    ): Result<AuthTokenResponse>

    @POST("/auth/register")
    suspend fun register(
        @Body regUserRequest: RegUserRequest
    ): Result<AuthTokenResponse>

    @Multipart
    @POST("/auth/register")
    suspend fun register(
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
    ): Result<AuthTokenResponse>
}
