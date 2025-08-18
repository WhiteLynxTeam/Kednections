package com.kednections.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kednections.data.network.api.UserApi
import com.kednections.data.network.dto.token.response.AuthTokenResponse
import com.kednections.data.network.dto.user.request.LoginUserRequest
import com.kednections.data.network.dto.user.request.RegUserRequest
import com.kednections.data.network.dto.user.response.UserCommunicationMethodResponse
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.CommunicationMethod
import com.kednections.domain.models.RegUser
import com.kednections.domain.models.Token
import com.kednections.domain.models.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UserRepository(
    private val userApi: UserApi,
) : IUserRepository {

    //[green] Переписать все мапперы в extension виде...

    override suspend fun login(user: User): Result<Token> {
        val result = userApi.login(mapperUserToUserDto(user))
        return result.map { mapperTokenResponseToToken(it) }
    }

    override suspend fun register(user: RegUser): Result<Token> {
        val result = userApi.register(user.toMultipartParts())
        return result.map { mapperTokenResponseToToken(it) }
    }

    override suspend fun getCommunicationMethod(): Result<List<CommunicationMethod>> {
        val result = userApi.getCommunicationMethod()
        return result.map { mapperListCommResponseToListComm(it) }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }


    private fun mapperCommResponseToComm(
        commResponse: UserCommunicationMethodResponse
    ): CommunicationMethod {
        return CommunicationMethod(
            id = commResponse.id,
            name = commResponse.name,
        )
    }

    private fun mapperListCommResponseToListComm(
        comms: List<UserCommunicationMethodResponse>
    ): List<CommunicationMethod> {
        return comms.map {
            mapperCommResponseToComm(it)
        }
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
            username = user.fio,
            password = user.password,
            description = user.description,
            nameDisplay = user.nameOrNick,
            city = user.city.id,
            tags = user.tags.map { it.id },
            communicationMethod = user.communicationMethod,
            specialization = user.specializations.map { it.id },
            nickname = user.nick,
            photo = user.photo,
            status = user.status,
        )
    }

    private fun RegUser.toMultipartParts(): Map<String, @JvmSuppressWildcards RequestBody> {
        val map = mutableMapOf<String, RequestBody>()

        //[red]бэк немного корявый, поэтому и мы жестко закинули uuid для всех полей?
        //чтобы проскачить регистрацию и перейти к главному функционалу

        //[yellow] бэк не проверяет совпадение по тэгам и специализации
        //забыл поменять при копировании uuid с communication_method
        //в тэгах и специализации - регистрация прошла
        map["email"] = this.username.toRequestBody("text/plain".toMediaType())
        map["username"] = this.fio.toRequestBody("text/plain".toMediaType())
        map["password"] = this.password.toRequestBody("text/plain".toMediaType())
        map["description"] = this.description.toRequestBody("text/plain".toMediaType())
        map["name_display"] = this.nameOrNick.value.toRequestBody("text/plain".toMediaType())
        map["city"] = "{\"id\" : \"${this.city.id}\"}".toRequestBody("text/plain".toMediaType())
        map["tags"] =
            "[{\"id\" : \"fda241ce-9fe9-4f6c-a396-a40a3510fcd6\"}]".toRequestBody("text/plain".toMediaType())
//        map["communication_method"] = this.communicationMethod.toRequestBody("text/plain".toMediaType())
        map["communication_method"] =
            "{\"id\" : \"462b89cd-950a-4681-a0b0-44eba9eb84cb\"}".toRequestBody("text/plain".toMediaType())
        map["specializations"] =
            "[{\"id\" : \"8f50a456-9cfa-40a9-a1f2-60da676a33e2\"}]".toRequestBody("text/plain".toMediaType())
        map["nickname"] = this.nick.toRequestBody("text/plain".toMediaType())
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