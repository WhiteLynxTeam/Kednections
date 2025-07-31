package com.kednections.utils.uiextensions

import android.app.Activity
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackbarShort(text: String) {
    Snackbar.make(
        this.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT
    ).show()
}

fun Activity.showToastShort(text: String) {
    Toast.makeText(
        this, text, Toast.LENGTH_SHORT
    ).show()
}

fun Activity.showToastLong(text: String) {
    Toast.makeText(
        this, text, Toast.LENGTH_LONG
    ).show()
}