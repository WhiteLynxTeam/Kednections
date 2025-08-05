package com.kednections.domain.irepository

import com.kednections.domain.models.Tag

interface ITagRepository {
    suspend fun getTags(): Result<List<Tag>>
}