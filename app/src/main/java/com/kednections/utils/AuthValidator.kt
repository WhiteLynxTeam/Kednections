package com.kednections.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

//*** [yellow] сделать нормально вынести enum в модели
enum class SelectedField { LOGIN, REG }

//*** прикрутить интерфейс с событиями onReg и onAuth. Пока так
interface AuthValidatorListener {
    fun onLoginSelected()
    fun onRegisterSelected()
}

class AuthValidator(
    private val field1: EditText, // email
    private val field2: EditText, // пароль
    private val actionButton: Button,
    private val image1: ImageView,
    private val image2: ImageView,
    private val length1: Int = 1,
    private val length2: Int = 8,
    private val image1SelectedTop: Int,
    private val image1SelectedBottom: Int,
    private val image2SelectedTop: Int,
    private val image2SelectedBottom: Int,
    private val listener: AuthValidatorListener? = null
) {

    companion object {
        const val PASSWORD_PATTERN = "^(?=.*[A-Za-zА-Яа-я])(?=.*\\d).+$"
        const val EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"

        const val MAX_EMAIL_LENGTH = 50
        const val MAX_PASSWORD_LENGTH = 50
        const val MIN_PASSWORD_LENGTH = 6

        fun isValidEmail(email: String): Boolean {
            return email.isNotBlank() &&
                    email.length <= MAX_EMAIL_LENGTH &&
                    Regex(EMAIL_PATTERN).matches(email.trim())
        }

        fun isValidPassword(password: String): Boolean {
            return password.isNotBlank() &&
                    password.length >= MIN_PASSWORD_LENGTH &&
                    password.length <= MAX_PASSWORD_LENGTH &&
                    Regex(PASSWORD_PATTERN).matches(password)
        }
    }

    private var selectedField: SelectedField? = null

    fun attach() {
        field1.addTextChangedListener(watcher)
        field2.addTextChangedListener(watcher)
        updateVisualStates()
        setupClickListeners()
    }


    fun getGelectedInt(): Int? {
        return selectedField?.ordinal
    }

    fun getSelectedField(): SelectedField? = selectedField

    //[yellow] Сделать проверку на валидность email
    fun validateEmail(): Boolean {
        val email = field1.text.toString().trim()
        return isValidEmail(email)
    }

    fun validatePassword(): Boolean {
        val password = field2.text.toString().trim()
        return isValidPassword(password)
    }

    fun validateAll(): Boolean {
        return validateEmail() && validatePassword()
    }

    fun getEmail(): String = field1.text.toString().trim()
    fun getPassword(): String = field2.text.toString().trim()

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = updateVisualStates()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun updateVisualStates() {
        val isLoginValid = (field1.text?.length ?: 0) >= length1
        val isRegValid = (field2.text?.length ?: 0) >= length2

        actionButton.isEnabled = isLoginValid && isRegValid

        when (selectedField) {
            SelectedField.LOGIN -> {
                image1.setImageResource(image1SelectedTop)
                image2.setImageResource(image2SelectedBottom)
            }
            SelectedField.REG -> {
                image2.setImageResource(image2SelectedTop)
                image1.setImageResource(image1SelectedBottom)
            }
            null -> {
                // если ничего не выбрано – дефолтные картинки
                image1.setImageResource(image1SelectedTop)
                image2.setImageResource(image2SelectedBottom)
            }
        }

        // Автоматический выбор активного, если оба поля валидны и ничего не выбрано
//        if (selectedField == null && isLoginValid && isRegValid) {
//            selectedField = SelectedField.LOGIN
//        }

        // Сброс selectedField, если выбранное поле стало невалидным
//        if ((selectedField == SelectedField.LOGIN && !isLoginValid) ||
//            (selectedField == SelectedField.REG && !isRegValid)
//        ) {
//            selectedField = null
//        }

        // Обновление визуального состояния рамки и кнопки

//        switcherBorder.setBackgroundResource(
//            if (isLoginValid && isRegValid)
//                R.drawable.bg_switcher_border
//            else
//                R.drawable.bg_switcher
//        )

        // Сценарий: оба поля валидны → отображаем как selected
//        if (isLoginValid && isRegValid) {
//            when (selectedField) {
//                SelectedField.LOGIN -> {
//                    image1.setImageResource(image1SelectedTop)
//                    image2.setImageResource(image2SelectedBottom)
//                }
//
//                SelectedField.REG -> {
//                    image2.setImageResource(image2SelectedTop)
//                    image1.setImageResource(image1SelectedBottom)
//                }
//
//                null -> {
//                    // Fallback, например если что-то пошло не так
//                    image1.setImageResource(image1Top)
//                    image2.setImageResource(image2Bottom)
//                }
//            }
//        } else {
//            // Только одно поле валидно или оба невалидны — возвращаем стандартные картинки
//            image1.setImageResource(if (isLoginValid) image1Top else image1Top)
//            image2.setImageResource(if (isRegValid) image2Bottom else image2Bottom)
//        }
    }

    private fun setupClickListeners() {
//        image1.setOnClickListener {
//            if ((field1.text?.length ?: 0) >= length1) {
//                selectedField = SelectedField.LOGIN
//                updateVisualStates()
//                listener?.onLoginSelected()
//            }
//        }
//
//        image2.setOnClickListener {
//            if ((field2.text?.length ?: 0) >= length2) {
//                selectedField = SelectedField.REG
//                updateVisualStates()
//                listener?.onRegisterSelected()
//            }
//        }
        image1.setOnClickListener {
            selectedField = SelectedField.LOGIN
            updateVisualStates()
            listener?.onLoginSelected()
        }

        image2.setOnClickListener {
            selectedField = SelectedField.REG
            updateVisualStates()
            listener?.onRegisterSelected()
        }
    }
}