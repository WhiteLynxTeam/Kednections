package com.kednections.view.feed.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.City
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterFeedViewModel @Inject constructor(
    private val getCitiesApiUseCase: GetCitiesApiUseCase
) : ViewModel() {

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities.asStateFlow()

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            _cities.value = getCitiesApiUseCase()
        }
    }

    class Factory @Inject constructor(
        private val getCitiesApiUseCase: GetCitiesApiUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilterFeedViewModel(getCitiesApiUseCase) as T
        }
    }
}