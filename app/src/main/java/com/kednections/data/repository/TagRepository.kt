package com.kednections.data.repository

import com.kednections.data.network.api.TagApi
import com.kednections.data.network.dto.tag.response.TagResponse
import com.kednections.data.network.dto.tag.response.fillIdByTitleFromResponse
import com.kednections.domain.irepository.ITagRepository
import com.kednections.domain.models.Tag

class TagRepository(
    private val tagApi: TagApi,
) : ITagRepository {

    override suspend fun getTags(): Result<List<Tag>> {
        val result = tagApi.tags()
        return result.map { mapperTagsResponseToTags(it) }
    }

    //[red] костыль. удалить после исправления бэка
    override suspend fun getTags(default: List<Tag>): Result<List<Tag>> {
        val result = tagApi.tags()
        return result.map { it.fillIdByTitleFromResponse(default) }
    }

    private fun mapperTagResponseToTag(
        tagResponse: TagResponse
    ): Tag {
        return Tag(
            id = tagResponse.id,
            title = tagResponse.name,
        )
    }

    private fun mapperTagsResponseToTags(
        tags: List<TagResponse>
    ): List<Tag> {
        return tags.map {
            mapperTagResponseToTag(it)
        }
    }
}