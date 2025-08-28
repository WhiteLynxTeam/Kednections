package com.kednections.domain.usecase.showcase

import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.models.showcase.PhotoShowCase
import com.kednections.domain.usecase.photo.GetPhotoApiUseCase
import com.kednections.domain.usecase.token.GetTokenPrefUseCase

class GetAllShowCaseApiUseCase(
    private val showCaseRepository: IShowCaseRepository,
    private val getTokenPrefUseCase: GetTokenPrefUseCase,
    private val getPhotoApiUseCase: GetPhotoApiUseCase,

    ) {
    suspend operator fun invoke(showcaseId: String): List<PhotoShowCase> {
        val token = getTokenPrefUseCase()
        val result = showCaseRepository.getAll(token, showcaseId)

        if (result.isSuccess) {
            val photos = result.getOrNull()
            if (photos != null) {


                return photos
            }
        }
        return emptyList()
    }
}