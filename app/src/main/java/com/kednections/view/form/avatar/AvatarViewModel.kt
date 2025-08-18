package com.kednections.view.form.avatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AvatarViewModel(
    ) : ViewModel() {


//      Сохраняет данные пользователя при нажатии кнопки
//      @param fio Полное имя пользователя
//      @param nick Никнейм пользователя
//      @param fioOrNick Выбранный вариант ("NAME" или "NICK")

/*    fun saveUserData(fio: String, nick: String, nameOrNick: NameOrNick) {
        saveUserDataPrefUseCase(fio, nick, nameOrNick)
    }*/

    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AvatarViewModel::class.java)) {
                return AvatarViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}