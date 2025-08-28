package com.kednections.domain.usecase.showcase

import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.models.showcase.PhotoShowCase

class UploadPhotoShowCaseApiUseCase(
    private val showCaseRepository: IShowCaseRepository,
    ) {
    suspend operator fun invoke(item: PhotoShowCase): Boolean {
        val result = showCaseRepository.upload(item)

        return result.isSuccess
    }
}