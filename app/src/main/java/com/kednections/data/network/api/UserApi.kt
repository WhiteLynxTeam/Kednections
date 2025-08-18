package com.kednections.data.network.api

import com.kednections.data.network.dto.token.response.AuthTokenResponse
import com.kednections.data.network.dto.user.request.LoginUserRequest
import com.kednections.data.network.dto.user.request.RegUserRequest
import com.kednections.data.network.dto.user.response.UserCommunicationMethodResponse
import com.kednections.data.network.dto.user.response.UserResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("/user/")
    suspend fun getUser(
        @Header("token") token: String,
    ): Result<UserResponse>

    @GET("/communication_method/")
    suspend fun getCommunicationMethod(): Result<List<UserCommunicationMethodResponse>>

    @Multipart
    @POST("/auth/register")
    suspend fun register(
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
    ): Result<AuthTokenResponse>
}
