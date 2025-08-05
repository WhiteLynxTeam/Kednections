package com.kednections.domain.irepository

import com.kednections.domain.models.City

interface IGeoRepository {
    suspend fun getCities(): Result<List<City>>
}