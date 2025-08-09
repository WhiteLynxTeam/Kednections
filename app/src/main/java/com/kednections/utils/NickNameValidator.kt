package com.kednections.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.kednections.R

class NickNameValidator(
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
    private enum class SelectedField { NAME, NICK }
    private var selectedField: SelectedField? = null

    fun attach() {
        field1.addTextChangedListener(watcher)
        field2.addTextChangedListener(watcher)
        updateVisualStates()
        setupClickListeners()
    }

    //*** [yellow] сделать нормально вынести enum в модели
    fun getSelected(): String? {
        return selectedField?.name
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = updateVisualStates()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun updateVisualStates() {
        val isNameValid = (field1.text?.length ?: 0) >= length1
        val isNickValid = (field2.text?.length ?: 0) >= length2

        // Автовыбор топового поля, если ещё ничего не выбрано
        if (selectedField == null) {
            selectedField = when {
                isNameValid -> SelectedField.NAME
                isNickValid -> SelectedField.NICK
                else -> null
            }
        }

        // Если выбранное поле стало невалидным — сбрасываем
        if ((selectedField == SelectedField.NAME && !isNameValid) ||
            (selectedField == SelectedField.NICK && !isNickValid)
        ) {
            selectedField = when {
                isNameValid -> SelectedField.NAME
                isNickValid -> SelectedField.NICK
                else -> null
            }
        }

        // UI состояния
        actionButton.isEnabled = isNameValid
        switcherBorder.setBackgroundResource(
            if (isNameValid || isNickValid)
                R.drawable.bg_switcher_border
            else
                R.drawable.bg_switcher
        )

        when (selectedField) {
            SelectedField.NAME -> {
                image1.setImageResource(image1SelectedTop)
                image2.setImageResource(
                    if (isNickValid) image2SelectedBottom else image2Bottom
                )
            }

            SelectedField.NICK -> {
                image2.setImageResource(image2SelectedTop)
                image1.setImageResource(
                    if (isNameValid) image1SelectedBottom else image1Bottom
                )
            }

            null -> {
                image1.setImageResource(image1Top)
                image2.setImageResource(image2Bottom)
            }
        }

        image1.isEnabled = isNameValid
        image2.isEnabled = isNickValid
    }


    private fun setupClickListeners() {
        image1.setOnClickListener {
            if ((field1.text?.length ?: 0) >= length1) {
                selectedField = SelectedField.NAME
                updateVisualStates()
            }
        }

        image2.setOnClickListener {
            if ((field2.text?.length ?: 0) >= length2) {
                selectedField = SelectedField.NICK
                updateVisualStates()
            }
        }
    }
}