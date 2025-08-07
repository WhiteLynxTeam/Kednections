package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.RegUser
import com.kednections.domain.models.Token
import com.kednections.domain.models.User

class RegisterUserApiUseCase(
    private val userRepository: IUserRepository,
) {
    suspend operator fun invoke(user: RegUser): Boolean {
        val result = userRepository.register(user)
        return result.isSuccess
    }
}