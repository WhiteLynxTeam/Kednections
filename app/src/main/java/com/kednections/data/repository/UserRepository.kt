package com.kednections.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kednections.data.network.api.UserApi
import com.kednections.data.network.dto.token.response.AuthTokenResponse
import com.kednections.data.network.dto.user.request.LoginUserRequest
import com.kednections.data.network.dto.user.request.RegUserRequest
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.RegUser
import com.kednections.domain.models.Specialization
import com.kednections.domain.models.Tag
import com.kednections.domain.models.Token
import com.kednections.domain.models.User

class UserRepository(
    private val userApi: UserApi,
) : IUserRepository {

    override suspend fun login(user: User): Result<Token> {
        val result = userApi.login(mapperUserToUserDto(user))
        return result.map { mapperTokenResponseToToken(it) }
    }

    override suspend fun register(user: RegUser): Result<Token> {
        val result = userApi.register(mapperRegUserToRegUserDto(user))
        return result.map { mapperTokenResponseToToken(it) }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
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

    private fun mapperRegUserToRegUserDto(
        user: RegUser
    ): RegUserRequest {
        return RegUserRequest(
            email = user.username,
            password = user.password,
            username = user.fio,
            nickname = user.nick,
            specialization = user.specializations.map { it.id },
            city = user.city.id,
            description = user.description,
            tags = user.tags.map { it.id },
            communication_method = user.communicationMethod,
            photo = user.photo,
            status = user.status,
        )
    }
}