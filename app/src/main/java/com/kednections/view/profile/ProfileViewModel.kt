package com.kednections.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.usecase.photo.GetPhotoApiUseCase
import com.kednections.domain.usecase.user.GetUserApiUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserApiUseCase: GetUserApiUseCase,
    private val getPhotoApiUseCase: GetPhotoApiUseCase,
) : ViewModel() {

//    val selectedImages = MutableLiveData<List<Uri>>()

    private var _photo = MutableSharedFlow<String>()
    val photo: SharedFlow<String>
        get() = _photo.asSharedFlow()

    fun getUser() {
        viewModelScope.launch {
            val user = getUserApiUseCase()
            if (user != null) {
                val photo = user.photo?.let { getPhotoApiUseCase(it) }
                println("ProfileViewModel photo = ${photo == null} ")
                if (photo != null) {

                    println("ProfileViewModel photo != null ")

                    _photo.emit(photo)
                }
            }
        }
    }

    class Factory(
        private val getUserApiUseCase: GetUserApiUseCase,
        private val getPhotoApiUseCase: GetPhotoApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(
                    getUserApiUseCase = getUserApiUseCase,
                    getPhotoApiUseCase = getPhotoApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}