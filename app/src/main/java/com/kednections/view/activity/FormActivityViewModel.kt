package com.kednections.view.activity

import androidx.lifecycle.ViewModel
import com.kednections.domain.models.RegUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormActivityViewModel : ViewModel() {
    private var _regUser = MutableStateFlow(RegUser())
    val regUser: StateFlow<RegUser>
        get() = _regUser.asStateFlow()

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

/*    fun register(user: User) {
        viewModelScope.launch {
            _isRegistry.emit(true)
        }
    }*/
}