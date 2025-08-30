package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository

class CheckUserApiUseCase(
    private val userRepository: IUserRepository,
    ) {
    //[yellow] Возвращаем Boolean - есть или нет пользователя, и второй переменной -
    // была ошибка или нет. Чтобы не пускать дальше на регистрацию, если была ошибка,
    // пусть вводит еще раз или нажимает кнопку, если сервер не работает
    suspend operator fun invoke(email: String): Pair<Boolean, Boolean> {
        val result = userRepository.verify(email)

        if (result.isSuccess) {
            val isIt = result.getOrNull()
            if (isIt != null) {
                return Pair(isIt, true)
            }
        }
        return Pair(false, false)
    }
}