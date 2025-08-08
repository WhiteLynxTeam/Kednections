package com.kednections.view.form.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AboutViewModel(
) : ViewModel() {

    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
                return AboutViewModel(
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}