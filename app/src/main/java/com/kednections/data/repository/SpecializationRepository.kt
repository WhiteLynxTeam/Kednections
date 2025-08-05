package com.kednections.data.repository

import com.kednections.data.network.api.SpecializationsApi
import com.kednections.data.network.dto.specialization.response.SpecializationResponse
import com.kednections.domain.irepository.ISpecializationRepository
import com.kednections.domain.models.Specialization

class SpecializationRepository(
    private val specializationApi: SpecializationsApi,
) : ISpecializationRepository {

    override suspend fun getSpecializations(): Result<List<Specialization>> {
        val result = specializationApi.specializations()
        return result.map { mapperSpecializationsResponseToSpecializations(it) }
    }

    private fun mapperSpecializationResponseToSpecialization(
        specializationResponse: SpecializationResponse
    ): Specialization {
        return Specialization(
            id = specializationResponse.id,
            name = specializationResponse.name,
        )
    }

    private fun mapperSpecializationsResponseToSpecializations(
        specialization: List<SpecializationResponse>
    ): List<Specialization> {
        return specialization.map {
            mapperSpecializationResponseToSpecialization(it)
        }
    }
}