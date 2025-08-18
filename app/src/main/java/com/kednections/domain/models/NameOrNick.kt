package com.kednections.domain.models

import com.google.gson.annotations.SerializedName

enum class NameOrNick(val value: String) {
    @SerializedName("username")
    NAME("username"),

    @SerializedName("nickname")
    NICK("nickname");

    companion object {
        fun fromName(name: String?): NameOrNick? =
            entries.find { it.name == name }

        fun fromValue(value: String?): NameOrNick? =
            entries.find { it.value == value }
    }
}