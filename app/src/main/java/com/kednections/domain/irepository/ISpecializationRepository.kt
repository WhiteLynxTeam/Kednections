package com.kednections.domain.irepository

import com.kednections.domain.models.Specialization

interface ISpecializationRepository {
    suspend fun getSpecializations(): Result<List<Specialization>>
}