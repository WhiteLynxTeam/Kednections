package com.kednections.view.form.specialization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.Specialization
import com.kednections.domain.usecase.specialization.GetSpecializationApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpecializationViewModel(
    private val getSpecializationApiUseCase: GetSpecializationApiUseCase,
) : ViewModel() {
    private var _specializations = MutableStateFlow<List<Specialization>>(emptyList())
    val specializations: StateFlow<List<Specialization>>
        get() = _specializations.asStateFlow()

    init {
        getSpecialization()
    }

    private fun getSpecialization() {
        viewModelScope.launch {
            _specializations.emit(getSpecializationApiUseCase())
            //[yellow] реализовывать вывод списка с сервера на экран пока не будет, всего 3 спринта
            println("_specializations = ${_specializations.value}")
        }
    }

    class Factory(
        private val getSpecializationApiUseCase: GetSpecializationApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SpecializationViewModel::class.java)) {
                return SpecializationViewModel(
                    getSpecializationApiUseCase = getSpecializationApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}