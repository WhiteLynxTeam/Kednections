package com.kednections.domain.usecase.user

import com.kednections.domain.istorage.IUserStorage


class SetFlagIsFirstPrefUseCase(
    private val storage: IUserStorage,
) {
    /*[green] Переделай функцию для установки флага.
     Ничего не возвращаем.
    * в storage вызываем соответствующие методы
    *
    * Добавляем остальные usecase в отдельных файлах для каждого сценария взаимодействия пользователя
    * с SharedPreferences. Не забываем для каждого сценария добавлять метод в модуль di DomainModule.

    : этo: установка флага что первый вход уже был, проверка флага первого входа,
    * и остальные если необходмы. Проверку и установку флага на аутентификацию не надо - за это
    * будут отвечать токены. Сделай флаг для проверки прохождения анкеты.
    *
    * Всю очистку настроек пока не делаем.
    * */
/*    operator fun invoke(): String? {
        val fullName = storage.getPhotoInfoUser()
        return fullName
    }*/
}