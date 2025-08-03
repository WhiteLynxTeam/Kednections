package com.kednections.domain.irepository

import com.google.firebase.auth.FirebaseUser


//  Репозиторий для работы с аутентификацией.
//  Определяет контракт для операций, связанных с аутентификацией пользователя.

interface IAuthRepository {


//    Получает текущего аутентифицированного пользователя.
//
//    @return Текущий пользователь Firebase, если он аутентифицирован, иначе null.

    fun getCurrentUser(): FirebaseUser?
}