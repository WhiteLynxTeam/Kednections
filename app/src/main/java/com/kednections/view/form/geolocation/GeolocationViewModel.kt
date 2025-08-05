package com.kednections.view.form.geolocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.City
import com.kednections.domain.models.Specialization
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeolocationViewModel(
    private val getCitiesApiUseCase: GetCitiesApiUseCase,
) : ViewModel() {
    private var _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>>
        get() = _cities.asStateFlow()

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch {
            _cities.emit(getCitiesApiUseCase())
            println("_cities = ${_cities.value}")
        }
    }

    class Factory(
        private val getCitiesApiUseCase: GetCitiesApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GeolocationViewModel::class.java)) {
                return GeolocationViewModel(
                    getCitiesApiUseCase = getCitiesApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}