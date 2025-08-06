package com.kednections.domain.usecase.tag

import com.kednections.domain.irepository.ITagRepository
import com.kednections.domain.models.Tag

class GetTagsApiUseCase(
    private val tagRepository: ITagRepository,
) {
    suspend operator fun invoke(): List<Tag> {
        val result = tagRepository.getTags()
        if (result.isSuccess) {
            val tags = result.getOrNull()
            if (tags != null) {
                return tags
            }
        }
        return emptyList()
    }
}