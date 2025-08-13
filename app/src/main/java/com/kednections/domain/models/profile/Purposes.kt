package com.kednections.domain.models.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Purposes(
    val icon: Int,
    val description: String
) : Parcelable
