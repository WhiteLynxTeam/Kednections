package com.kednections.domain.irepository

import com.kednections.domain.models.Token
import com.kednections.domain.models.showcase.PhotoShowCase

interface IShowCaseRepository {
    suspend fun upload(token: Token, item: PhotoShowCase): Result<String>
    suspend fun delete(token: Token, itemId: String): Result<Boolean>
    suspend fun update(token: Token, item: PhotoShowCase): Result<String>
    suspend fun getAll(token: Token, showcaseId: String): Result<List<PhotoShowCase>>
}