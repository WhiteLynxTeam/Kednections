package com.kednections.data.network.api

import com.kednections.data.network.dto.token.response.AuthTokenResponse
import com.kednections.data.network.dto.user.request.LoginUserRequest
import com.kednections.data.network.dto.user.request.RegTempUserRequest
import com.kednections.data.network.dto.user.request.RegUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/auth/login")
    suspend fun login(
        @Body loginUserRequest: LoginUserRequest
    ): Result<AuthTokenResponse>

    @POST("/auth/register")
    suspend fun register(
        @Body regUserRequest: RegTempUserRequest
    ): Result<AuthTokenResponse>
}
