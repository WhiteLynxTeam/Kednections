package com.kednections.view.profile.showcase

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kednections.domain.models.showcase.PhotoShowCase
import com.kednections.domain.usecase.showcase.UploadListPhotoShowCaseApiUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ShowCaseViewModel(
    private val uploadListPhotoShowCaseApiUseCase: UploadListPhotoShowCaseApiUseCase,
    private val contentResolver: ContentResolver,
) : ViewModel() {
    private val _isUpload = MutableSharedFlow<Boolean>()
    val isUpload: SharedFlow<Boolean> = _isUpload

    fun uploadAll(listPhotoShowCase: List<Uri>) {

        //[red] создать модель для передачи названия, описания и файла
        val itemShowCase = listPhotoShowCase.map {
            PhotoShowCase(
                photo = try {
                    contentResolver.openInputStream(it)?.use { inputStream ->
                        inputStream.readBytes()
                    }
//                    Base64.encodeToString(bitmap, Base64.DEFAULT)
//                    bitmap
                }  catch (e: Exception) {
                    e.printStackTrace()
//                    ""
                    null
                }
            )
        }

        viewModelScope.launch {
            uploadListPhotoShowCaseApiUseCase(itemShowCase)
            //[yellow] Сделать обработку успешной загрузки
            _isUpload.emit(true)
        }
    }

    class Factory(
        private val uploadListPhotoShowCaseApiUseCase: UploadListPhotoShowCaseApiUseCase,
        private val contentResolver: ContentResolver,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShowCaseViewModel::class.java)) {
                return ShowCaseViewModel(
                    uploadListPhotoShowCaseApiUseCase = uploadListPhotoShowCaseApiUseCase,
                    contentResolver = contentResolver,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}