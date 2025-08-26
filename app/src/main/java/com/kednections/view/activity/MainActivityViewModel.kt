package com.kednections.view.activity

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.user.UserProfile
import com.kednections.domain.usecase.user.GetUserApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getUserApiUseCase: GetUserApiUseCase,
) : ViewModel() {

    private var _user = MutableStateFlow<UserProfile?>(null)
    val user: StateFlow<UserProfile?>
        get() = _user.asStateFlow()

    private var _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    val selectedImages: StateFlow<List<Uri>>
        get() = _selectedImages.asStateFlow()

    private var _selectedPosition = MutableStateFlow<Int>(0)
    val selectedPosition: StateFlow<Int>
        get() = _selectedPosition.asStateFlow()

    private var _isProfileTop = MutableStateFlow<Boolean>(true)
    val isProfileTop: StateFlow<Boolean>
        get() = _isProfileTop.asStateFlow()

    private var _originalImages = MutableStateFlow<List<Uri>>(emptyList())
    val originalImages: StateFlow<List<Uri>>
        get() = _originalImages.asStateFlow()

    fun saveImages(uris: List<Uri>, position: Int = 0) {
        _selectedImages.value = uris.toList()
        _selectedPosition.value = position
        // Сохраняем также как оригинальные
        _originalImages.value = uris.toList()
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

    fun setOriginalImages(uris: List<Uri>) {
        _originalImages.value = uris.toList()
    }

    fun hasChanges(): Boolean {
        // Сравниваем текущие изображения с оригинальными
        return _selectedImages.value != _originalImages.value
    }

    fun saveChanges() {
        // Сохраняем текущее состояние как оригинальное
        _originalImages.value = _selectedImages.value.toList()
    }

    fun discardChanges() {
        // Восстанавливаем оригинальные изображения
        _selectedImages.value = _originalImages.value.toList()
    }

    fun getUser() {
        viewModelScope.launch {
//            val user = getUserApiUseCase()
            _user.emit(getUserApiUseCase())
            /*//            if (user != null) {

                            val photo = user.photo?.let { getPhotoApiUseCase(it) }
                            val status = user.status
                            println("ProfileViewModel photo = ${photo == null} ")
                            if (photo != null) {

                                println("ProfileViewModel photo != null ")

                                _icon.emit(Pair(photo, null))
                            } else {
                                _icon.emit(Pair(null, status))
                            }
                        }*/
        }
    }

    class Factory(
        private val getUserApiUseCase: GetUserApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(
                    getUserApiUseCase = getUserApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}