package com.kednections.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kednections.data.network.api.UserApi
import com.kednections.data.network.dto.token.response.AuthTokenResponse
import com.kednections.data.network.dto.user.request.LoginUserRequest
import com.kednections.data.network.dto.user.request.RegTempUserRequest
import com.kednections.data.network.dto.user.request.RegUserRequest
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.RegUser
import com.kednections.domain.models.Token
import com.kednections.domain.models.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UserRepository(
    private val userApi: UserApi,
) : IUserRepository {

    override suspend fun login(user: User): Result<Token> {
        val result = userApi.login(mapperUserToUserDto(user))
        return result.map { mapperTokenResponseToToken(it) }
    }

    override suspend fun register(user: RegUser): Result<Token> {
        val result = userApi.register(user.toMultipartParts())
        return result.map { mapperTokenResponseToToken(it) }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    private fun mapperTokenResponseToToken(
        authTokenResponse: AuthTokenResponse
    ): Token {
        return Token(
            token = authTokenResponse.access_token,
        )
    }

    private fun mapperUserToUserDto(
        user: User
    ): LoginUserRequest {
        return LoginUserRequest(
            email = user.username,
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

    private fun RegUser.toMultipartParts(): Map<String, @JvmSuppressWildcards RequestBody> {
        val map = mutableMapOf<String, RequestBody>()

        map["email"] = this.username.toRequestBody("text/plain".toMediaType())
        map["password"] = this.password.toRequestBody("text/plain".toMediaType())
        map["username"] = this.fio.toRequestBody("text/plain".toMediaType())
        map["nickname"] = this.nick.toRequestBody("text/plain".toMediaType())
        map["city"] = "{\"id\" : \"${this.city.id}\"}".toRequestBody("text/plain".toMediaType())
        map["description"] = this.description.toRequestBody("text/plain".toMediaType())
//        map["communication_method"] = this.communicationMethod.toRequestBody("text/plain".toMediaType())
        map["communication_method"] =
            "{\"id\" : \"462b89cd-950a-4681-a0b0-44eba9eb84cb\"}".toRequestBody("text/plain".toMediaType())
        map["tags"] =
            "[{\"id\" : \"462b89cd-950a-4681-a0b0-44eba9eb84cb\"}]".toRequestBody("text/plain".toMediaType())
        map["specializations"] =
            "[{\"id\" : \"462b89cd-950a-4681-a0b0-44eba9eb84cb\"}]".toRequestBody("text/plain".toMediaType())

        // map["specialization"] = this.specialization.joinToString(",").toRequestBody("text/plain".toMediaType())
        // map["tags"] = this.tags.joinToString(",").toRequestBody("text/plain".toMediaType())

        // Опциональные поля со строками
        this.status?.let {
            map["status"] = it.toRequestBody("text/plain".toMediaType())
        }
        this.photo?.let {
            map["photo"] = it.toRequestBody("text/plain".toMediaType())
        }

        return map
    }
}