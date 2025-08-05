package com.kednections.domain.usecase.geo

import com.kednections.domain.irepository.IGeoRepository
import com.kednections.domain.models.City

class GetCitiesApiUseCase(
    private val geoRepository: IGeoRepository,
) {
    suspend operator fun invoke(): List<City> {
        val result = geoRepository.getCities()
        if (result.isSuccess) {
            val cities = result.getOrNull()
            if (cities != null) {
                return cities
            }
        }
        return emptyList()
    }
}