package com.kednections.data.repository

import com.kednections.data.network.api.TagApi
import com.kednections.data.network.dto.tag.response.TagResponse
import com.kednections.domain.irepository.ITagRepository
import com.kednections.domain.models.Tag

class TagRepository(
    private val tagApi: TagApi,
) : ITagRepository {

    override suspend fun getTags(): Result<List<Tag>> {
        val result = tagApi.tags()
        return result.map { mapperTagsResponseToTags(it) }
    }

    private fun mapperTagResponseToTag(
        tagResponse: TagResponse
    ): Tag {
        return Tag(
            id = tagResponse.id,
            name = tagResponse.name,
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