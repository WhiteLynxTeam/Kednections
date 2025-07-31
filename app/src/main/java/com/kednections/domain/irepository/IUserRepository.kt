package com.kednections.domain.irepository

import com.kednections.domain.models.Token
import com.kednections.domain.models.User

interface IUserRepository {
    suspend fun login(user: User): Result<Token>
    suspend fun register(user: User): Result<Token>
}