package com.kednections.domain.usecase.user

import com.google.firebase.auth.FirebaseUser
import com.kednections.domain.irepository.IUserRepository
import javax.inject.Inject


//  Use case для получения текущего аутентифицированного пользователя.
//  Инкапсулирует логику получения информации о текущем пользователе.
class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: IUserRepository // Доступ к репозиторию
) {
//     Выполняет операцию получения текущего пользователя.
    operator fun invoke(): FirebaseUser? = authRepository.getCurrentUser()
}