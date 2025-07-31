package com.kednections.utils.uiextensions

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showToastShort(text: String) {
    requireActivity().showToastShort(text)
}

fun Fragment.showToastShort(resId: Int) {
    requireActivity().showToastShort(getString(resId))
}

fun Fragment.showToastLong(text: String) {
    requireActivity().showToastLong(text)
}

fun Fragment.showSnackbarShort(text: String) {
    Snackbar.make(
        requireActivity().findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.showSnackbarLong(text: String) {
    Snackbar.make(
        requireActivity().findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG
    ).show()
}

fun Fragment.showSnackbarWithAction(
    text: String, actionText: String, action: View.OnClickListener
) {
    Snackbar.make(
        requireActivity().findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT
    ).setAction(actionText, action).show()
}

