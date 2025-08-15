package com.kednections.domain.usecase.user

import com.kednections.domain.istorage.IUserStorage


//  UseCase для сохранения данных пользователя в SharedPreferences.
//  Сохраняем ФИО, ник и состояние переключателя.

class SaveUserDataPrefUseCase(
    private val userStorage: IUserStorage
) {
    operator fun invoke(fio: String, nick: String, fioOrNick: String) {
        // Сохраняем данные через существующий интерфейс
        userStorage.saveFio(fio)
        userStorage.saveNick(nick)
        userStorage.saveFioOrNickSelection(fioOrNick)
    }
}