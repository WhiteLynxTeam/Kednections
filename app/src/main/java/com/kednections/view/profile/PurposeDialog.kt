package com.kednections.view.profile

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kednections.R
import com.kednections.databinding.DialogPurposesBinding
import com.kednections.domain.models.profile.Purposes

class PurposeDialog : DialogFragment() {
    private var _binding: DialogPurposesBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(purposes: List<Purposes>): PurposeDialog {
            val args = Bundle().apply {
                putParcelableArrayList("purposes_list", ArrayList(purposes))
            }
            return PurposeDialog().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogPurposesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialog)

        dialog?.window?.let { window ->
            // Устанавливаем 90% ширины экрана
            val width = (resources.displayMetrics.widthPixels * 0.903).toInt()
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawableResource(R.color.background_color)

            // Центрируем диалог
            window.setGravity(Gravity.CENTER)
        }

        val purposesList = arguments?.getParcelableArrayList<Purposes>("purposes_list") ?: emptyList()

        binding.rcPurposes.layoutManager = LinearLayoutManager(requireContext())
        binding.rcPurposes.adapter = PurposeAdapter(purposesList)

        binding.ivClosed.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}