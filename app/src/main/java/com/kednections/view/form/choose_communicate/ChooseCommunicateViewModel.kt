package com.kednections.view.form.choose_communicate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChooseCommunicateViewModel(
) : ViewModel() {

    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChooseCommunicateViewModel::class.java)) {
                return ChooseCommunicateViewModel(
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}