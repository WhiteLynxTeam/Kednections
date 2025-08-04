package com.kednections.view.form.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NickNameViewModel(
    ) : ViewModel() {

    class Factory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NickNameViewModel::class.java)) {
                return NickNameViewModel(

                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}