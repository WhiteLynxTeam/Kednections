package com.kednections.domain.irepository

import com.google.firebase.auth.FirebaseUser
import com.kednections.domain.models.CommunicationMethod
import com.kednections.domain.models.user.RegUser
import com.kednections.domain.models.Token
import com.kednections.domain.models.user.User
import com.kednections.domain.models.user.UserProfile

interface IUserRepository {
    suspend fun login(user: User): Result<Token>
    suspend fun register(user: RegUser): Result<Token>
    suspend fun getUser(token: Token): Result<UserProfile>
    fun getCurrentUser(): FirebaseUser?
    suspend fun getCommunicationMethod(): Result<List<CommunicationMethod>>
}