package com.kednections.view.feed

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDetail(
    val imageRes: Int,
    val title: String = "",
    val description: String = "",
    val likes: Int = 0
) : Parcelable