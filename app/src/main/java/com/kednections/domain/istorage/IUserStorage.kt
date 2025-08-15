package com.kednections.domain.istorage


interface IUserStorage {
    // Свойство для первого запуска
    var isFirstRun: Boolean

    // Метод для установки флага первого запуска как завершенного
    fun setFirstRunCompleted(bool: Boolean)

    // Метод для проверки статуса анкеты
    fun isQuestionnaireCompleted(): Boolean

    // Метод для установки флага анкеты
    fun setQuestionnaireCompleted(completed: Boolean)

    // Методы для сохранения данных
    fun saveFio(fio: String)
    fun saveNick(nick: String)
    fun saveFioOrNickSelection(selection: String)
}
