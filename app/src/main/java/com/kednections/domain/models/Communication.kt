package com.kednections.domain.models

import com.google.gson.annotations.SerializedName

enum class Communication(var uuid: String?, var value: String?, var isCheck: Boolean = false) {
    @SerializedName("username")
    ONLINE(null, null),

    @SerializedName("nickname")
    OFFLINE(null, null);

    companion object {
        fun setCommunication(uuid: String, text: String) {
            if (text.isNotEmpty()) {
                if(text.contains(Regex("онлайн|online", RegexOption.IGNORE_CASE))) {
                    ONLINE.uuid = uuid
                    ONLINE.value = text
                } else {
                    OFFLINE.uuid = uuid
                    OFFLINE.value = text
                }
            }
        }

        //[green] Не нравится мне эта функция и Enum
        fun getCheckedUuid(): String? {
            return entries.firstOrNull { it.isCheck }?.uuid
        }
    }
}