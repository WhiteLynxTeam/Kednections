package com.kednections.domain.irepository

import com.kednections.domain.models.Token
import com.kednections.domain.models.showcase.PhotoShowCase

interface IShowCaseRepository {
    suspend fun upload(item: PhotoShowCase): Result<String>
    suspend fun getAll(token: Token, showcaseId: String): Result<List<PhotoShowCase>>
}