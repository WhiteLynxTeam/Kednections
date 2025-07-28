package com.kednections.view.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WelcomeViewModel(
    /*[green] от файрбасе одна переменная, как во фрагменте с авторизацией здесь,
    * и куча переменных с usecase, которые используем в этом фрагменте
    *
    * не забываем проставить переменные в модуле di AppModule
    *
    *
    * */

) : ViewModel() {
    /*[green] создаем переменную поток, за которой будем следить во фрагменте с помощью collect
    * Как только меняется, то сразу делаем действие, например, переходим на другой фрагмент
    * Смотрим аналогию в kmx3
    * */

    class Factory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
                return WelcomeViewModel(

                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}