package com.kednections.view.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.user.RegUser
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormActivityViewModel(
    private val registerUserApiUseCase: RegisterUserApiUseCase,
    ) : ViewModel() {
    private var _regUser = MutableStateFlow(RegUser())
    val regUser: StateFlow<RegUser>
        get() = _regUser.asStateFlow()

    private var _isReg = MutableSharedFlow<Boolean>()
    val isReg: SharedFlow<Boolean>
        get() = _isReg.asSharedFlow()

    private val _progress = MutableStateFlow(1)
    val progress: StateFlow<Int> = _progress

    fun increaseProgress() {
        _progress.value += 1
    }

    fun decreaseProgress() {
        if (_progress.value > 1) {
            _progress.value -= 1
        }
    }

    fun updateData(transform: (RegUser) -> RegUser) {
        _regUser.update { current -> transform(current) }
        println("_regUser ${_regUser.value}")
    }

    fun register() {
        viewModelScope.launch {
            val result = registerUserApiUseCase(_regUser.value)
            println("_regUser ${_regUser.value}")
            _isReg.emit(result)
        }
    }

    class Factory(
        private val registerUserApiUseCase: RegisterUserApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FormActivityViewModel::class.java)) {
                return FormActivityViewModel(
                    registerUserApiUseCase = registerUserApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}