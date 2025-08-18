package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.user.User
import com.kednections.domain.usecase.token.SaveTokenPrefUseCase

class LoginUserApiUseCase(
    private val userRepository: IUserRepository,
    private val saveTokenPrefUseCase: SaveTokenPrefUseCase,
    ) {
    suspend operator fun invoke(user: User): Boolean {
        val result = userRepository.login(user)

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