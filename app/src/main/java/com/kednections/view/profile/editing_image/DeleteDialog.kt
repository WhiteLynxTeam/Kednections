package com.kednections.view.profile.editing_image

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import com.kednections.R

class DeleteDialog(
    private val onConfirm: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_delete_image, null)
        builder.setView(view)

        val dialog = builder.create()

        view.findViewById<AppCompatButton>(R.id.delete).setOnClickListener {
            onConfirm.invoke()
            dialog.dismiss()
        }

        view.findViewById<AppCompatButton>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    }
}
