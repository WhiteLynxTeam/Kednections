package com.kednections.view.profile.editing_image

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import com.kednections.databinding.DialogBackInEditingBinding

class BackInEditingDialog(
    private val onSave: () -> Unit,
    private val onDiscard: () -> Unit
) : DialogFragment() {

    private var _binding: DialogBackInEditingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        _binding = DialogBackInEditingBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root

        builder.setView(view)
        val dialog = builder.create()

        binding.yes.setOnClickListener {
            onSave.invoke()
            dialog.dismiss()
        }

        binding.no.setOnClickListener {
            onDiscard.invoke()
            dialog.dismiss()
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}