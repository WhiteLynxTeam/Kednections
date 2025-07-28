package com.kednections.data.storage

import android.content.SharedPreferences
import com.kednections.domain.istorage.IUserStorage
import androidx.core.content.edit

class UserStorage(
    private val sharedPreferences: SharedPreferences
) : IUserStorage {
//[green] Добавь логику. Можно конечно сделать с переменными. А можно сократить код в функциях.
    // Флаг первого запуска приложения (по умолчанию true)
/*    private var _isFirstRun: Boolean
        get() = sharedPreferences.getBoolean("is_first_run", true)
        set(value) = sharedPreferences.edit().putBoolean("is_first_run", value).apply()*/

    fun isFirstRun(): Boolean {
        return sharedPreferences.getBoolean("is_first_run", true)
    }

    fun setFirstRun(value: Boolean) {
//        _isFirstRun = value
        sharedPreferences.edit() { putBoolean("is_first_run", value) }
    }

    //[green] Сделай константы под ключ
    companion object {
/*        private const val FULL_NAME = "full_name"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        private const val PHOTO = "photo"
        private const val OFFICE_NAME = "office_name"
        private const val OFFICE_LOCATION = "office_location"
        private const val DEPARTMENT = "department"*/
    }
}
