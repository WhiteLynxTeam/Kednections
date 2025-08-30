package com.kednections.domain.usecase.showcase

import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.models.showcase.PhotoShowCase
import com.kednections.domain.usecase.token.GetTokenPrefUseCase

class UploadPhotoShowCaseApiUseCase(
    private val showCaseRepository: IShowCaseRepository,
    private val getTokenPrefUseCase: GetTokenPrefUseCase,
    ) {
    suspend operator fun invoke(item: PhotoShowCase): Boolean {
        val token = getTokenPrefUseCase()
        val result = showCaseRepository.upload(token ,item)

        return result.isSuccess
    }
}