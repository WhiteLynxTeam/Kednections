package com.kednections.view.feed.filter

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import com.kednections.R

class NothingFilterDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_nothing_filter, null)
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

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = Dialog(requireContext())
//        dialog.setContentView(R.layout.dialog_nothing_filter)
//
//        // Обработчик на иконку закрытия
//        dialog.findViewById<ImageView>(R.id.iv_closed).setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
//        dialog.window?.setDimAmount(0.5f)
//        return dialog
//    }
}