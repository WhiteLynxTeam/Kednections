package com.kednections.domain.usecase.showcase

import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.usecase.token.GetTokenPrefUseCase

class DeletePhotoShowCaseApiUseCase (
    private val showCaseRepository: IShowCaseRepository,
    private val getTokenPrefUseCase: GetTokenPrefUseCase,
) {
    suspend operator fun invoke(itemId: String): Boolean {
        val token = getTokenPrefUseCase()
        val result = showCaseRepository.delete(token, itemId)

        return result.isSuccess
    }
}