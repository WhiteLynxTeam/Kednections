package com.kednections.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.showcase.PhotoShowCase
import com.kednections.domain.usecase.showcase.GetAllShowCaseApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getAllShowCaseApiUseCase: GetAllShowCaseApiUseCase,
) : ViewModel() {
    private var _itemShowCase = MutableStateFlow<List<PhotoShowCase>>(emptyList())
    val itemShowCase: StateFlow<List<PhotoShowCase>>
        get() = _itemShowCase.asStateFlow()

    fun getAllShowCase(showcaseId: String) {
        viewModelScope.launch {

            println("*** ProfileViewModel getAllShowCase showcaseId = $showcaseId")

            _itemShowCase.emit(getAllShowCaseApiUseCase(showcaseId).filter {it.photo!= null && it.photo.isNotEmpty() })
        }
    }

    class Factory(
        private val getAllShowCaseApiUseCase: GetAllShowCaseApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(
                    getAllShowCaseApiUseCase = getAllShowCaseApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}