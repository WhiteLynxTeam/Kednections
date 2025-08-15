package com.kednections.view.form.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kednections.domain.usecase.user.SaveUserDataPrefUseCase

class NickNameViewModel(
    private val saveUserDataPrefUseCase: SaveUserDataPrefUseCase
    ) : ViewModel() {


//      Сохраняет данные пользователя при нажатии кнопки
//      @param fio Полное имя пользователя
//      @param nick Никнейм пользователя
//      @param fioOrNick Выбранный вариант ("NAME" или "NICK")

    fun saveUserData(fio: String, nick: String, fioOrNick: String) {
        saveUserDataPrefUseCase(fio, nick, fioOrNick)
    }

    class Factory(
        private val saveUserDataPrefUseCase: SaveUserDataPrefUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NickNameViewModel::class.java)) {
                return NickNameViewModel(saveUserDataPrefUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}