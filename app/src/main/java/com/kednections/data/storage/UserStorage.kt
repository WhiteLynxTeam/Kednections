package com.kednections.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.models.NameOrNick

class UserStorage(
    private val sharedPreferences: SharedPreferences
) : IUserStorage {

    companion object {
        // Константы для ключей SharedPreferences
        private const val KEY_FIRST_RUN = "is_first_run"
        private const val KEY_QUESTIONNAIRE_COMPLETED = "questionnaire_completed"
        private const val KEY_FIO = "user_fio"
        private const val KEY_NICK = "user_nick"
        private const val KEY_FIO_OR_NICK = "fio_or_nick_selection"
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

    override fun saveFio(fio: String) {
        sharedPreferences.edit {
            putString(KEY_FIO, fio)
        }
    }

    override fun saveNick(nick: String) {
        sharedPreferences.edit {
            putString(KEY_NICK, nick)
        }
    }

    override fun saveFioOrNickSelection(selection: NameOrNick) {
        sharedPreferences.edit {
            putString(KEY_FIO_OR_NICK, selection.name)
        }
    }

    override fun getFioOrNickSelection() : NameOrNick? {
        val name = sharedPreferences.getString(KEY_FIO_OR_NICK, null)
        return NameOrNick.fromName(name)
    }
}

