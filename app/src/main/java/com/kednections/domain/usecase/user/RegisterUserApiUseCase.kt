package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.RegUser
import com.kednections.domain.usecase.token.SaveTokenPrefUseCase

class RegisterUserApiUseCase(
    private val userRepository: IUserRepository,
    private val saveTokenPrefUseCase: SaveTokenPrefUseCase,
) {
    suspend operator fun invoke(user: RegUser): Boolean {
        val result = userRepository.register(user)

        if (result.isSuccess) {
            val token = result.getOrNull()
            if (token != null) {
                saveTokenPrefUseCase(token.token)

                return true
            }
        }
        return false
    }
}