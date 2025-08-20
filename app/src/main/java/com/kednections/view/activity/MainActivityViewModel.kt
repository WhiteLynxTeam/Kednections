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

    private var _selectedPosition = MutableStateFlow<Int>(0)
    val selectedPosition: StateFlow<Int>
        get() = _selectedPosition.asStateFlow()

    private var _isProfileTop = MutableStateFlow<Boolean>(true)
    val isProfileTop: StateFlow<Boolean>
        get() = _isProfileTop.asStateFlow()

    fun saveImages(uris: List<Uri>, position: Int = 0) {
        _selectedImages.value = uris.toList() // новый инстанс
        _selectedPosition.value = position
    }

    fun replaceImageAt(index: Int, newUri: Uri) {
        val list = _selectedImages.value.toMutableList()
        if (index in list.indices) {
            list[index] = newUri
            _selectedImages.value = list // новый инстанс -> emission гарантирован
            _selectedPosition.value = index
        }
    }

    fun removeImageAt(index: Int) {
        val list = _selectedImages.value.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            _selectedImages.value = list // новый инстанс
            val newPos = when {
                list.isEmpty() -> 0
                index > list.lastIndex -> list.lastIndex
                else -> index
            }
            _selectedPosition.value = newPos
        }
    }

    fun setSelectedPosition(position: Int) {
        _selectedPosition.value = position
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