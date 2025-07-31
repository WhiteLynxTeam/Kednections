package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.Token
import com.kednections.domain.models.User

class LoginUserApiUseCase(
    private val userRepository: IUserRepository,
) {
    suspend operator fun invoke(user: User): Token? {
        val result = userRepository.login(user)
        if (result.isSuccess) {
            val token = result.getOrNull()
            if (token != null) {
                return token
            }
        }
        return null
    }
}