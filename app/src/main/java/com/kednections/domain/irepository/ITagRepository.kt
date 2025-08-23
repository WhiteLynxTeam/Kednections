package com.kednections.domain.irepository

import com.kednections.domain.models.Tag

interface ITagRepository {
    suspend fun getTags(): Result<List<Tag>>
    //[red] костыль. удалить после исправления бэка
    suspend fun getTags(default: List<Tag>): Result<List<Tag>>
}