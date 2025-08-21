package com.kednections.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.usecase.photo.GetPhotoApiUseCase
import com.kednections.domain.usecase.user.GetUserApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserApiUseCase: GetUserApiUseCase,
    private val getPhotoApiUseCase: GetPhotoApiUseCase,
) : ViewModel() {

//    val selectedImages = MutableLiveData<List<Uri>>()
//    val isProfileTop = MutableLiveData(true)

    private var _icon = MutableStateFlow<Pair<String?,String?>>(Pair(null, null))
    val icon: StateFlow<Pair<String?,String?>>
        get() = _icon.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            val user = getUserApiUseCase()
            if (user != null) {
                val photo = user.photo?.let { getPhotoApiUseCase(it) }
                val status = user.status
                println("ProfileViewModel photo = ${photo == null} ")
                if (photo != null) {

                    println("ProfileViewModel photo != null ")

                    _icon.emit(Pair(photo, null))
                } else {
                    _icon.emit(Pair(null, status))
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