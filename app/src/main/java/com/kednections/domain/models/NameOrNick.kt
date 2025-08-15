package com.kednections.domain.models

import com.google.gson.annotations.SerializedName

enum class NameOrNick {
    @SerializedName("username")
    NAME,

    @SerializedName("nickname")
    NICK,
}