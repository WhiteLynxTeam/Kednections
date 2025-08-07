package com.kednections.domain.models.feed

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDetail(
    val imageRes: Int,
    val comment: String
) : Parcelable