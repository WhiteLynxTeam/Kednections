package com.kednections.view.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    val selectedImages = MutableLiveData<List<Uri>>()
    val isProfileTop = MutableLiveData(true)
}