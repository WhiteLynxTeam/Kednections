package com.kednections.domain.models.user

import com.kednections.domain.models.AvaPhoto
import com.kednections.domain.models.City
import com.kednections.domain.models.CommunicationMethod
import com.kednections.domain.models.NameOrNick
import com.kednections.domain.models.Specialization
import com.kednections.domain.models.Tag

data class UserProfile(
    val username: String? = "",
    val fio: String? = "",
    val nick: String? = "",
    var photo: AvaPhoto? = null,
//    val specializations: List<String>? = emptyList(),
    //[yellow] все var сделаны для того, чтобы подтягивать название по id
    var specializations: List<Specialization> = emptyList(),
    var city: City,
    val description: String? = "",
//    val tags: List<String>? = emptyList(),
    var tags: List<Tag> = emptyList(),
    var communicationMethod: CommunicationMethod,
    val nameOrNick: NameOrNick,
    val status: String? = null,
)