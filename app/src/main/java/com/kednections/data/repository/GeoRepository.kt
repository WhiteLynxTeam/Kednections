package com.kednections.data.repository

import com.kednections.data.network.api.GeoApi
import com.kednections.data.network.dto.geo.response.CityResponse
import com.kednections.domain.irepository.IGeoRepository
import com.kednections.domain.models.City

class GeoRepository(
    private val geoApi: GeoApi,
) : IGeoRepository {

    override suspend fun getCities(): Result<List<City>> {
        val result = geoApi.cities()
        return result.map { mapperCitiesResponseToCities(it) }
    }

    private fun mapperCityResponseToCity(
        cityResponse: CityResponse
    ): City {
        return City(
            id = cityResponse.id,
            name = cityResponse.name,
        )
    }

    private fun mapperCitiesResponseToCities(
        cities: List<CityResponse>
    ): List<City> {
        return cities.map {
            mapperCityResponseToCity(it)
        }
    }
}