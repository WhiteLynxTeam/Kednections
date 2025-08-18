package com.kednections.domain.usecase.user

import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.models.NameOrNick


//  UseCase для сохранения данных пользователя в SharedPreferences.
//  Сохраняем ФИО, ник и состояние переключателя.

class SaveUserDataPrefUseCase(
    private val userStorage: IUserStorage
) {
    operator fun invoke(fio: String, nick: String, nameOrNick: NameOrNick) {
        // Сохраняем данные через существующий интерфейс
        userStorage.saveFio(fio)
        userStorage.saveNick(nick)
        userStorage.saveFioOrNickSelection(nameOrNick)
    }
}