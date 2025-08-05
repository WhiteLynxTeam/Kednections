package com.kednections.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.kednections.domain.istorage.IUserStorage

class UserStorage(
    private val sharedPreferences: SharedPreferences
) : IUserStorage {

    companion object {
        // Константы для ключей SharedPreferences
        private const val KEY_FIRST_RUN = "is_first_run"
        private const val KEY_QUESTIONNAIRE_COMPLETED = "questionnaire_completed"
    }

    // Реализация для флага первого запуска
    override var isFirstRun: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_RUN, true)
        set(value) {
            sharedPreferences.edit { putBoolean(KEY_FIRST_RUN, value) }
        }

    // Первый запуск завершен
    override fun setFirstRunCompleted(bool: Boolean) {
        isFirstRun = false
    }

    // Реализация для флага анкеты
    private var _isQuestionnaireCompleted: Boolean
        get() = sharedPreferences.getBoolean(KEY_QUESTIONNAIRE_COMPLETED, false)
        set(value) {
            sharedPreferences.edit { putBoolean(KEY_QUESTIONNAIRE_COMPLETED, value) }
        }

    // Метод для проверки статуса анкеты
    override fun isQuestionnaireCompleted(): Boolean = _isQuestionnaireCompleted

    // Метод для установки флага анкеты
    override fun setQuestionnaireCompleted(completed: Boolean) {
        _isQuestionnaireCompleted = completed
    }

    //[green] Сделай константы под ключ
//    companion object {
/*        private const val FULL_NAME = "full_name"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        private const val PHOTO = "photo"
        private const val OFFICE_NAME = "office_name"
        private const val OFFICE_LOCATION = "office_location"
        private const val DEPARTMENT = "department"*/
    }

