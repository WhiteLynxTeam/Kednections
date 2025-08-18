package com.kednections.domain.models

data class RegUser(
    val username: String = "",
    val password: String  = "",
    val fio: String  = "",
    val nick: String  = "",
    val nameOrNick: NameOrNick  = NameOrNick.NAME,
    val specializations: List<Specialization> = emptyList(),
    val city: City  = City("",""),
    val description: String  = "",
    val tags: List<Tag> = emptyList(),
    val communicationMethod: String  = "",
    val photo: String?  = null,
    val status: String?  = null,
)