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
    private val validImageRes1Bottom: Int = R.drawable.ic_name_switcher_selected_bottom,
    private val validImageRes2Bottom: Int = R.drawable.ic_nick_switcher_selected_bottom,
    private val selectedImageRes1Top: Int = R.drawable.ic_name_switcher_selected_top,
    private val selectedImageRes2Top: Int = R.drawable.ic_nick_switcher_selected_top,
    private val invalidImageRes1Top: Int = R.drawable.ic_name_switcher_top,
    private val invalidImageRes1Bottom: Int = R.drawable.ic_name_switcher_bottom,
    private val invalidImageRes2Top: Int = R.drawable.ic_nick_switcher_top,
    private val invalidImageRes2Bottom: Int = R.drawable.ic_nick_switcher_bottom,
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

        if (selectedImageView == null) {
            if (nameValidNow && !isNameValid) {
                selectedImageView = image1
            } else if (nickValidNow && !isNickValid) {
                selectedImageView = image2
            }
        }

        isNameValid = nameValidNow
        isNickValid = nickValidNow

        // Border background
        if (isNameValid || isNickValid) {
            switcherBorder.setBackgroundResource(R.drawable.bg_switcher_border)
        } else {
            switcherBorder.setBackgroundResource(R.drawable.bg_auth_input)
        }

        actionButton.isEnabled = isNameValid

        // image1 (имя)
        if (isNameValid) {
            image1.setImageResource(getValidOrSelectedImage(image1))
            image1.isEnabled = true
        } else {
            image1.setImageResource(getInvalidImageRes(image1))
            image1.isEnabled = false
            if (selectedImageView == image1) selectedImageView = null
        }

        // image2 (ник)
        if (isNickValid) {
            image2.setImageResource(getValidOrSelectedImage(image2))
            image2.isEnabled = true
        } else {
            image2.setImageResource(getInvalidImageRes(image2))
            image2.isEnabled = false
            if (selectedImageView == image2) selectedImageView = null
        }
    }

    private fun getValidOrSelectedImage(image: ImageView): Int {
        return if (selectedImageView == image) {
            getSelectedImageRes(image)
        } else {
            getValidImageRes(image)
        }
    }

    private fun getTopImageView(): ImageView {
        return when {
            selectedImageView != null -> selectedImageView!!
            isNameValid && !isNickValid -> image1
            !isNameValid && isNickValid -> image2
            else -> image1 // по умолчанию имя сверху
        }
    }

    private fun isTop(image: ImageView): Boolean {
        return getTopImageView() == image
    }

    private fun getInvalidImageRes(image: ImageView): Int {
        return when {
            image == image1 && isTop(image) -> invalidImageRes1Top
            image == image1 -> invalidImageRes1Bottom
            image == image2 && isTop(image) -> invalidImageRes2Top
            else -> invalidImageRes2Bottom
        }
    }

    private fun getValidImageRes(image: ImageView): Int {
        return when {
            image == image1 -> validImageRes1Bottom
            image == image2 -> validImageRes2Bottom
            else -> error("Unexpected image")
        }
    }

    private fun getSelectedImageRes(image: ImageView): Int {
        return when {
            image == image1 && isTop(image) -> selectedImageRes1Top
            image == image2 && isTop(image) -> selectedImageRes2Top
            else -> error("Selected image must be top")
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
