package com.kednections.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class FormValidator(
    private val field1: EditText,
    private val field2: EditText,
    private val actionButton: Button,
    private val length1: Int = 1,
    private val length2: Int = 8
) {

    // Основная логика активации кнопки
    private fun updateButtonState() {
        val isEmailValid = (field1.text?.length ?: 0) >= length1
        val isPasswordValid = (field2.text?.length ?: 0) >= length2
        actionButton.isEnabled = isEmailValid && isPasswordValid
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = updateButtonState()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    // Вызвать для активации логики
    fun attach() {
        field1.addTextChangedListener(watcher)
        field2.addTextChangedListener(watcher)
        updateButtonState() // первичная проверка при инициализации
    }
}