package com.kednections.data.network.dto.user.request

import com.google.gson.annotations.SerializedName
import com.kednections.domain.models.NameOrNick

data class RegUserRequest(
    // логин
    val email: String,
    // фамилия имя - фио
    val username: String,
    val password: String,
    val description: String,
    val city: String,
    // способ общения - онлайн или в живую - может быть только одно
    @SerializedName("communication_method")
    val communicationMethod: String,
    // что отображать фио или ник, в профиле для себя и для других
    @SerializedName("name_display")
    val nameDisplay: NameOrNick,
    // тэги - что ищем, какие цели
    val tags: List<String>,
    // дизайнерские специализации - максимум 3
    val specialization: List<String>,
    val nickname: String,
    val photo: String? = null,
    val status: String? = null,
)