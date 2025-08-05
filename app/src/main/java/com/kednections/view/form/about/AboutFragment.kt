package com.kednections.view.form.about

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentAboutBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivity
import com.kednections.view.activity.FormActivityViewModel
import dagger.android.support.AndroidSupportInjection

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FormActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        binding.etAbout.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentLength = s?.length ?: 0
                binding.tvCharCount.text = "$currentLength/200"

                binding.btnResume.isEnabled = currentLength >= 1
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnResume.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_successRegFragment)
        }

        binding.skipped.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_successRegFragment)
        }

        (activity as FormActivity).setUIVisibility(
            showHeader = true
        )

    }

}