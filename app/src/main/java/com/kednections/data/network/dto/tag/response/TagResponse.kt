package com.kednections.data.network.dto.tag.response

import com.kednections.domain.models.Tag

data class TagResponse(
    val id: String,
    val name: String,
)

//[red] костыль. удалить после исправления бэка
fun List<TagResponse>.fillIdByTitleFromResponse(tags: List<Tag>): List<Tag> {
    val map = this.associateBy({ it.name }, { it.id })
    return tags.map { tag ->
        tag.copy(id = map[tag.title] ?: tag.id)
    }
}
