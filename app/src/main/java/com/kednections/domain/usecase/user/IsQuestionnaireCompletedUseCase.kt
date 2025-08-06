package com.kednections.domain.usecase.user

import com.kednections.domain.istorage.IUserStorage
import javax.inject.Inject


//  Use case для проверки завершения анкеты пользователем.
//  Инкапсулирует логику проверки статуса прохождения анкеты.
class IsQuestionnaireCompletedUseCase @Inject constructor(
    private val storage: IUserStorage
) {
//     Проверяет, прошел ли пользователь анкету.
    operator fun invoke(): Boolean = storage.isQuestionnaireCompleted()
}