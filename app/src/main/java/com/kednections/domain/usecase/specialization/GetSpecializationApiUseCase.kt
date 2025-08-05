package com.kednections.domain.usecase.specialization

import com.kednections.domain.irepository.ISpecializationRepository
import com.kednections.domain.models.Specialization

class GetSpecializationApiUseCase(
    private val specializationRepository: ISpecializationRepository,
) {
    suspend operator fun invoke(): List<Specialization> {
        val result = specializationRepository.getSpecializations()
        if (result.isSuccess) {
            val specialization = result.getOrNull()
            if (specialization != null) {
                return specialization
            }
        }
        return emptyList()
    }
}