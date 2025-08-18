package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.CommunicationMethod

class GetCommMethodApiUseCase(
    private val userRepository: IUserRepository,
) {
    suspend operator fun invoke(): List<CommunicationMethod> {
        val result = userRepository.getCommunicationMethod()
        if (result.isSuccess) {
            val comms = result.getOrNull()
            if (comms != null) {
                return comms
            }
        }
        return emptyList()
    }
}