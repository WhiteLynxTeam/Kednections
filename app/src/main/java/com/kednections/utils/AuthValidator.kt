package com.kednections.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.kednections.R

class AuthValidator(
    private val field1: EditText, // имя
    private val field2: EditText, // ник
    private val actionButton: Button,
    private val image1: ImageView,
    private val image2: ImageView,
    private val length1: Int = 1,
    private val length2: Int = 8,
    private val switcherBorder: LinearLayout,
    private val image1Top: Int,
    private val image1Bottom: Int,
    private val image1SelectedTop: Int,
    private val image1SelectedBottom: Int,
    private val image2Bottom: Int,
    private val image2SelectedTop: Int,
    private val image2SelectedBottom: Int
) {
    private enum class SelectedField { LOGIN, REG }
    private var selectedField: SelectedField? = null

    fun attach() {
        field1.addTextChangedListener(watcher)
        field2.addTextChangedListener(watcher)
        updateVisualStates()
        setupClickListeners()
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = updateVisualStates()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun updateVisualStates() {
        val isLoginValid = (field1.text?.length ?: 0) >= length1
        val isRegValid = (field2.text?.length ?: 0) >= length2

        // Автоматический выбор активного, если оба поля валидны и ничего не выбрано
        if (selectedField == null && isLoginValid && isRegValid) {
            selectedField = SelectedField.LOGIN
        }

        // Сброс selectedField, если выбранное поле стало невалидным
        if ((selectedField == SelectedField.LOGIN && !isLoginValid) ||
            (selectedField == SelectedField.REG && !isRegValid)
        ) {
            selectedField = null
        }

        // Обновление визуального состояния рамки и кнопки
        actionButton.isEnabled = isLoginValid && isRegValid
        switcherBorder.setBackgroundResource(
            if (isLoginValid && isRegValid)
                R.drawable.bg_switcher_border
            else
                R.drawable.bg_switcher
        )

        // Сценарий: оба поля валидны → отображаем как selected
        if (isLoginValid && isRegValid) {
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
                    // Fallback, например если что-то пошло не так
                    image1.setImageResource(image1Top)
                    image2.setImageResource(image2Bottom)
                }
            }
        } else {
            // Только одно поле валидно или оба невалидны — возвращаем стандартные картинки
            image1.setImageResource(if (isLoginValid) image1Top else image1Top)
            image2.setImageResource(if (isRegValid) image2Bottom else image2Bottom)
        }
    }

    private fun setupClickListeners() {
        image1.setOnClickListener {
            if ((field1.text?.length ?: 0) >= length1) {
                selectedField = SelectedField.LOGIN
                updateVisualStates()
            }
        }

        image2.setOnClickListener {
            if ((field2.text?.length ?: 0) >= length2) {
                selectedField = SelectedField.REG
                updateVisualStates()
            }
        }
    }
}