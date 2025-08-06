package com.kednections.data.repository

import com.google.firebase.auth.FirebaseUser
import com.kednections.data.network.api.UserApi
import com.kednections.data.network.dto.token.response.AuthTokenResponse
import com.kednections.data.network.dto.user.request.LoginUserRequest
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.Token
import com.kednections.domain.models.User

class UserRepository(
    private val userApi: UserApi,
) : IUserRepository {

    override suspend fun login(user: User): Result<Token> {
        val result = userApi.login(mapperUserToUserDto(user))
        return result.map { mapperTokenResponseToToken(it) }
    }

    override suspend fun register(user: User): Result<Token> {
        val result = userApi.register(mapperUserToUserDto(user))
        return result.map { mapperTokenResponseToToken(it) }
    }

    override fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    private fun mapperTokenResponseToToken(
        authTokenResponse: AuthTokenResponse
    ): Token {
        return Token(
            token = authTokenResponse.token,
        )
    }

    private fun mapperUserToUserDto(
        user: User
    ): LoginUserRequest {
        return LoginUserRequest(
            username = user.username,
            password = user.password,
        )
    }
}