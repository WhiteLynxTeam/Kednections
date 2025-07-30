package com.kednections.view.form.geolocation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentGeolocationBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import dagger.android.support.AndroidSupportInjection

class GeolocationFragment : Fragment() {

    private var _binding: FragmentGeolocationBinding? = null
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

        _binding = FragmentGeolocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        binding.btnResume.setOnClickListener {
            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)
            viewModel.increaseProgress()
        }

        binding.skipped.setOnClickListener {
            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)
            viewModel.increaseProgress()
        }
    }

}