package com.kednections.domain.usecase.user

import com.kednections.domain.istorage.IUserStorage
import javax.inject.Inject


//  Use case для проверки, является ли текущий запуск приложения первым.
//  Инкапсулирует логику проверки флага первого запуска.
class GetIsFirstRunUseCase @Inject constructor(
    private val storage: IUserStorage
) {
//      Выполняет проверку первого запуска приложения.
    operator fun invoke(): Boolean = storage.isFirstRun
}