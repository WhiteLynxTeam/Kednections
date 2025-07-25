package com.kednections.view.activity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FormActivityViewModel : ViewModel() {

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
}