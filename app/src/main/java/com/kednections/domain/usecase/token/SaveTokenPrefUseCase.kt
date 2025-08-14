package com.kednections.domain.usecase.token

import com.kednections.domain.istorage.ITokenStorage

class SaveTokenPrefUseCase(
    private val tokenStorage: ITokenStorage
) {
    operator fun invoke(token: String) {
        tokenStorage.saveToken(token)
    }
}
