package com.kednections.view.feed.filter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentFilterFeedBinding
import com.kednections.view.activity.MainActivity
import dagger.android.support.AndroidSupportInjection

class FilterFeedFragment : Fragment() {

    private var _binding: FragmentFilterFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedViews: MutableSet<TextView>

    private lateinit var radioGroup: RadioGroup

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilterFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedViews = mutableSetOf()

        radioGroup = binding.radioGroup

        val specializationIds = listOf(
            R.id.art_director, R.id.illustrator, R.id.three_d_designer,
            R.id.web_designer, R.id.game_designer, R.id.artist,
            R.id.graphic_designer, R.id.interior_designer, R.id.ui_designer,
            R.id.fashion_designer, R.id.print_designer, R.id.presentation_designer,
            R.id.communication_designer, R.id.brand_designer, R.id.landscape_designer,
            R.id.designer, R.id.product_designer, R.id.ux_ui_designer,
            R.id.industrial_designer, R.id.fpx_designer, R.id.ai_designer,
            R.id.ar_vr_designer, R.id.motion_designer, R.id.smm_designer
        )

        specializationIds.forEach { id ->
            val textView = view.findViewById<TextView>(id)
            textView.setOnClickListener {
                if (selectedViews.contains(textView)) {
                    deselectView(textView)
                    selectedViews.remove(textView)
                } else {
                    selectView(textView)
                    selectedViews.add(textView)
                }
                // Обновляем состояние кнопки "Применить"
                updateApplyButtonState()
            }
        }

        // Обработчик изменений в RadioGroup (формат общения)
        radioGroup.setOnCheckedChangeListener { _, _ ->
            // При изменении выбора обновляем кнопку
            updateApplyButtonState()
        }

        // Обработчик изменений текста в поле геопозиции
        binding.etGeoposition.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // После изменения текста обновляем кнопку
                updateApplyButtonState()
            }
        })


        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnResume.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imgLogo.setOnClickListener {
            findNavController().navigate(R.id.action_filterFeedFragment_to_screenSaverFragment)
        }

        (activity as MainActivity).setUIVisibility(
            showHeader = true
        )

        binding.btnReset.setOnClickListener {
            resetAllFilters()
        }
    }

    private fun updateApplyButtonState() {
        // Проверяем, выбран ли хотя бы один формат общения
        val isCommunicationFormatSelected = radioGroup.checkedRadioButtonId != -1
        // Кнопка активна, если выбрана специализация ИЛИ формат общения
        binding.btnResume.isEnabled = selectedViews.isNotEmpty() || isCommunicationFormatSelected
        binding.etGeoposition.length() > 0 || isCommunicationFormatSelected
    }

    private fun resetAllFilters() {
        // Сбрасываем выбранные специализации
        selectedViews.forEach { textView ->
            deselectView(textView)
        }
        selectedViews.clear()

        // Сбрасываем выбор формата общения
        radioGroup.clearCheck()

        // Сбрасываем поле геопозиции
        binding.etGeoposition.text?.clear()

        // Обновляем состояние кнопки
        updateApplyButtonState()
    }


    private fun selectView(view: TextView) {
        view.setBackgroundResource(R.drawable.bg_for_text_specializations_selected)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun deselectView(view: TextView) {
        view.setBackgroundResource(R.drawable.bg_for_text_specializations)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_description))
    }

}