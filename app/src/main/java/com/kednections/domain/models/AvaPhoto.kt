package com.kednections.domain.models

data class AvaPhoto(
    val id: String,
    val photo: String?,
) {
    //[green] Чтобы не выводить поле с файлом в консоль
    override fun toString(): String {
        return "AvaPhoto(id='$id', photo = 'file')"
    }
}