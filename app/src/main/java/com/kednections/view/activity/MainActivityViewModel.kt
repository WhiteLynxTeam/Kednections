package com.kednections.view.activity

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel(
    ) : ViewModel() {
    private var _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    val selectedImages: StateFlow<List<Uri>>
        get() = _selectedImages.asStateFlow()

    private var _isProfileTop = MutableStateFlow<Boolean>(true)
    val isProfileTop: StateFlow<Boolean>
        get() = _isProfileTop.asStateFlow()

    fun saveImages(uris: MutableList<Uri>){
        _selectedImages.value = uris
    }

    fun setIsProfileTop(flag: Boolean) {
        _isProfileTop.value = flag
    }

    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}