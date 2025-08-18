package com.kednections.view.form.choose_communicate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.Communication
import com.kednections.domain.models.CommunicationMethod
import com.kednections.domain.usecase.user.GetCommMethodApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChooseCommunicateViewModel(
    private val getCommMethodApiUseCase: GetCommMethodApiUseCase
) : ViewModel() {
    private var _comms = MutableStateFlow<List<CommunicationMethod>>(emptyList())
    val comms: StateFlow<List<CommunicationMethod>>
        get() = _comms.asStateFlow()

    init {
        getCommsMethod()
    }

    private fun getCommsMethod() {
        viewModelScope.launch {
            val result = getCommMethodApiUseCase()
            result.forEach {
                Communication.setCommunication(it.id, it.name)
            }
            _comms.emit(result)
        }
    }

    class Factory(
        private val getCommMethodApiUseCase: GetCommMethodApiUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChooseCommunicateViewModel::class.java)) {
                return ChooseCommunicateViewModel(
                    getCommMethodApiUseCase = getCommMethodApiUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}