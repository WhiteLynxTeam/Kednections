package com.kednections.domain.usecase.user

import com.kednections.domain.istorage.IUserStorage
import javax.inject.Inject

// Use case для установки флага первого запуска как завершенного.
// После вызова этого метода приложение будет считать, что первый запуск уже был.
class SetFirstRunCompletedUseCase @Inject constructor(
    private val storage: IUserStorage
) {
//    Выполняет операцию установки флага первого запуска в false.
//    Это означает, что приложение больше не будет считать текущий запуск первым.
    operator fun invoke() = storage.setFirstRunCompleted(true)
}