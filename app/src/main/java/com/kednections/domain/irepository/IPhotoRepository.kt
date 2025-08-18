package com.kednections.domain.irepository

import com.kednections.domain.models.Token

interface IPhotoRepository {
    suspend fun getFile(token: Token, uuid: String): Result<String>
}