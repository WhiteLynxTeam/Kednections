package com.kednections.view.form.specialization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentSpecializationBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SpecializationFragment : Fragment() {

    private var _binding: FragmentSpecializationBinding? = null
    private val binding get() = _binding!!
    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: SpecializationViewModel

    @Inject
    lateinit var vmFactory: SpecializationViewModel.Factory

    private lateinit var selectedViews: MutableSet<TextView>
    private val maxSelection = 3

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSpecializationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, vmFactory)[SpecializationViewModel::class.java]

        selectedViews = mutableSetOf()

        val specializationIds = listOf(
            R.id.tv_art_director, R.id.tv_illustrator, R.id.tv_3d_designer,
            R.id.tv_web_designer, R.id.tv_game_designer, R.id.tv_artist,
            R.id.tv_graphic_designer, R.id.tv_interior_designer, R.id.tv_ui_designer,
            R.id.tv_fashion_designer, R.id.tv_print_designer, R.id.tv_presentation_designer,
            R.id.tv_communication_designer, R.id.tv_brand_designer, R.id.tv_landscape_designer,
            R.id.tv_designer, R.id.tv_product_designer, R.id.tv_ux_ui_designer,
            R.id.tv_industrial_designer, R.id.tv_fpx_designer, R.id.tv_ai_designer,
            R.id.tv_ar_vr_designer, R.id.tv_motion_designer, R.id.tv_smm_designer
        )

        specializationIds.forEach { id ->
            val textView = view.findViewById<TextView>(id)
            textView.setOnClickListener {
                if (selectedViews.contains(textView)) {
                    deselectView(textView)
                    selectedViews.remove(textView)
                } else {
                    if (selectedViews.size >= maxSelection) {
                        showLimitReachedDialog()
                        return@setOnClickListener
                    }
                    // Select
                    selectView(textView)
                    selectedViews.add(textView)
                }

                binding.btnResume.isEnabled = selectedViews.isNotEmpty()
            }
        }

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)


        binding.btnResume.setOnClickListener {
            activityViewModel.updateData {
                it.copy(
                    //[red] заглушка для проверки регистрации - передаем первые три элемента
                    specializations = viewModel.specializations.value.take(3)
                )
            }
            findNavController().navigate(R.id.action_specializationFragment_to_geolocationFragment)
            activityViewModel.increaseProgress()
        }

    }

    private fun selectView(view: TextView) {
        view.setBackgroundResource(R.drawable.bg_for_text_specializations_selected)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun deselectView(view: TextView) {
        view.setBackgroundResource(R.drawable.bg_for_text_specializations)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_description))
    }

    private fun showLimitReachedDialog() {
        LimitReachedDialog().show(parentFragmentManager, "LimitDialog")
    }
}