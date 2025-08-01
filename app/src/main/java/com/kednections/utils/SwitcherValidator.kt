package com.kednections.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.kednections.R

class SwitcherValidator(
    private val field1: EditText,
    private val field2: EditText,
    private val actionButton: Button,
    private val image1: ImageView,
    private val image2: ImageView,
    private val length1: Int = 1,
    private val length2: Int = 8,
    private val switcherBorder: LinearLayout,
    private val invalidImageRes1: Int = R.drawable.ic_name_switcher,
    private val invalidImageRes2: Int = R.drawable.ic_nick_switcher,
    private val validImageRes1: Int = R.drawable.ic_name_switcher_enabled,
    private val validImageRes2: Int = R.drawable.ic_nick_switcher_enabled,
    private val selectedImageRes1: Int = R.drawable.ic_name_switcher_selected,
    private val selectedImageRes2: Int = R.drawable.ic_nick_switcher_selected
) {
    private var selectedImageView: ImageView? = null

    private var isNameValid = false
    private var isNickValid = false

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
        val nameValidNow = (field1.text?.length ?: 0) >= length1
        val nickValidNow = (field2.text?.length ?: 0) >= length2

        // Автоустановка selectedImageView если ранее ничего не выбрано
        if (selectedImageView == null) {
            if (nameValidNow && !isNameValid) {
                selectedImageView = image1
            } else if (nickValidNow && !isNickValid) {
                selectedImageView = image2
            }
        }

        isNameValid = nameValidNow
        isNickValid = nickValidNow

        if (isNameValid && isNickValid) {
            switcherBorder.setBackgroundResource(R.drawable.bg_switcher_border)
        } else switcherBorder.setBackgroundResource(R.drawable.bg_auth_input)

        actionButton.isEnabled = isNameValid

        // Логика для image1 (имя)
        if (isNameValid) {
            if (selectedImageView == image1) {
                image1.setImageResource(selectedImageRes1)
            } else {
                image1.setImageResource(validImageRes1)
            }
            image1.isEnabled = true
        } else {
            image1.setImageResource(invalidImageRes1)
            image1.isEnabled = false
            if (selectedImageView == image1) selectedImageView = null
        }

        // Логика для image2 (ник)
        if (isNickValid) {
            if (selectedImageView == image2) {
                image2.setImageResource(selectedImageRes2)
            } else {
                image2.setImageResource(validImageRes2)
            }
            image2.isEnabled = true
        } else {
            image2.setImageResource(invalidImageRes2)
            image2.isEnabled = false
            if (selectedImageView == image2) selectedImageView = null
        }
    }


    private fun setupClickListeners() {
        image1.setOnClickListener {
            if (!isNameValid) return@setOnClickListener

            selectedImageView = image1
            updateVisualStates()
        }

        image2.setOnClickListener {
            if (!isNickValid) return@setOnClickListener

            selectedImageView = image2
            updateVisualStates()
        }
    }
}
