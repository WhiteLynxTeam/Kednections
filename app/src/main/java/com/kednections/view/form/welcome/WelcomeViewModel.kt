package com.kednections.view.form.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.kednections.domain.usecase.user.GetCurrentUserUseCase
import com.kednections.domain.usecase.user.GetIsFirstRunUseCase
import com.kednections.domain.usecase.user.SetFirstRunCompletedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getIsFirstRunUseCase: GetIsFirstRunUseCase,
    private val setFirstRunCompletedUseCase: SetFirstRunCompletedUseCase
) : ViewModel() {

    // Поток для событий навигации.
    private val _navigationEvent = MutableSharedFlow<NavigationDestination>()
    val navigationEvent: SharedFlow<NavigationDestination> = _navigationEvent


    sealed class NavigationDestination {
        object Auth : NavigationDestination()
        object Main : NavigationDestination()
    }


    fun checkUserStateAndNavigate() {
        viewModelScope.launch {
            // Получаем текущего пользователя через use case
            val currentUser: FirebaseUser? = getCurrentUserUseCase()

            // Проверяем, первый ли это запуск приложения
            val isFirstRun: Boolean = getIsFirstRunUseCase()

            when {
                // Пользователь авторизован и это не первый запуск
                currentUser != null && !isFirstRun -> {
                    _navigationEvent.emit(NavigationDestination.Main)
                }

                // Новый пользователь или сброс приложения
                else -> {
                    // Если это первый запуск, помечаем его как завершенный
                    if (isFirstRun) {
                        setFirstRunCompletedUseCase()
                    }
                    _navigationEvent.emit(NavigationDestination.Auth)
                }
            }
        }
    }


    class Factory(
        private val getCurrentUserUseCase: GetCurrentUserUseCase,
        private val getIsFirstRunUseCase: GetIsFirstRunUseCase,
        private val setFirstRunCompletedUseCase: SetFirstRunCompletedUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // Проверяем, что запрашивается именно WelcomeViewModel
            if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
                return WelcomeViewModel(
                    getCurrentUserUseCase,
                    getIsFirstRunUseCase,
                    setFirstRunCompletedUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}