package com.kednections.view.form.specialization

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import com.kednections.R

class LimitReachedDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_limit_reached, null)
        builder.setView(view)

        val dialog = builder.create()

        // Обработчик на иконку закрытия
        view.findViewById<ImageView>(R.id.iv_closed).setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        // Прозрачный фон у окна
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    }
}

