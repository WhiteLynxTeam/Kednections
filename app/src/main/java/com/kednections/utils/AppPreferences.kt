package com.kednections.utils

import android.content.Context
import android.content.SharedPreferences

// Класс для хранения настроек приложения
class AppPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Флаг первого запуска приложения (по умолчанию true)
    var isFirstRun: Boolean
        get() = prefs.getBoolean("is_first_run", true)
        set(value) = prefs.edit().putBoolean("is_first_run", value).apply()

    // Флаг авторизации пользователя (по умолчанию false)
    var isUserAuthenticated: Boolean
        get() = prefs.getBoolean("is_user_authenticated", false)
        set(value) = prefs.edit().putBoolean("is_user_authenticated", value).apply()

    // Очистка всех сохраненных настроек
    fun clear() {
        prefs.edit().clear().apply()
    }
}