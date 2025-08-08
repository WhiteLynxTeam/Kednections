package com.kednections.view.form.purposes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.Tag
import com.kednections.domain.usecase.tag.GetTagsApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PurposesViewModel(
    private val getTagsApiUseCase: GetTagsApiUseCase,
) : ViewModel() {
    private var _tags = MutableStateFlow<List<Tag>>(emptyList())
    val tags: StateFlow<List<Tag>>
        get() = _tags.asStateFlow()

    init {
        getTags()
    }

    private fun getTags() {
        viewModelScope.launch {
            _tags.emit(getTagsApiUseCase())
            println("_tags = ${_tags.value}")
        }
    }

    class Factory(
        private val getTagsApiUseCase: GetTagsApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PurposesViewModel::class.java)) {
                return PurposesViewModel(
                    getTagsApiUseCase = getTagsApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}