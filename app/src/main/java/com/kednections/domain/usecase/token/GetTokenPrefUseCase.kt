package com.kednections.domain.usecase.token

import com.kednections.domain.istorage.ITokenStorage
import com.kednections.domain.models.Token

class GetTokenPrefUseCase(
    private val tokenStorage: ITokenStorage
) {
    operator fun invoke(): Token {
        val token = tokenStorage.getToken()
        return Token(token = token)
    }
}