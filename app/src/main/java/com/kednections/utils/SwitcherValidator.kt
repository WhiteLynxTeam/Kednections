package com.kednections.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kednections.R

class SwitcherValidator(
    private val field1: EditText,
    private val field2: EditText,
    private val actionButton: Button,
    private val image1: ImageView,
    private val image2: ImageView,
    private val length1: Int = 1,
    private val length2: Int = 8,
    private val validImageRes1: Int = R.drawable.ic_name_switcher_enabled,
    private val validImageRes2: Int = R.drawable.ic_nick_switcher_enabled,
    private val invalidImageRes1: Int = R.drawable.ic_name_switcher,
    private val invalidImageRes2: Int = R.drawable.ic_nick_switcher
) {

    private fun updateButtonState() {
        val isEmailValid = (field1.text?.length ?: 0) >= length1
        val isPasswordValid = (field2.text?.length ?: 0) >= length2
        val isValid = isEmailValid && isPasswordValid

        actionButton.isEnabled = isValid

        if (isValid) {
            image1.setImageResource(validImageRes1)
            image2.setImageResource(validImageRes2)
        } else {
            image1.setImageResource(invalidImageRes1)
            image2.setImageResource(invalidImageRes2)
        }
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = updateButtonState()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    fun attach() {
        field1.addTextChangedListener(watcher)
        field2.addTextChangedListener(watcher)
        updateButtonState()
    }
}
