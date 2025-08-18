package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.user.UserProfile
import com.kednections.domain.usecase.token.GetTokenPrefUseCase

class GetUserApiUseCase(
    private val userRepository: IUserRepository,
    private val getTokenPrefUseCase: GetTokenPrefUseCase
) {
    suspend operator fun invoke(): UserProfile? {
        val token = getTokenPrefUseCase()
        val result = userRepository.getUser(token)

        println("GetUserApiUseCase result = $result")

        if (result.isSuccess) {
            val user = result.getOrNull()
            if (user != null) {
                println("GetUserApiUseCase user = $user")

                return user
            }
        }
        return null
    }
}