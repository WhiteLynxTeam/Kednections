package com.kednections.domain.irepository

import com.google.firebase.auth.FirebaseUser
import com.kednections.domain.models.CommunicationMethod
import com.kednections.domain.models.RegUser
import com.kednections.domain.models.Token
import com.kednections.domain.models.User

interface IUserRepository {
    suspend fun login(user: User): Result<Token>
    suspend fun register(user: RegUser): Result<Token>
    fun getCurrentUser(): FirebaseUser?
    suspend fun getCommunicationMethod(): Result<List<CommunicationMethod>>
}