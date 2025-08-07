package com.kednections.view.form.geolocation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentGeolocationBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

class GeolocationFragment : Fragment() {

    private var _binding: FragmentGeolocationBinding? = null
    private val binding get() = _binding!!
    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: GeolocationViewModel

    @Inject
    lateinit var vmFactory: GeolocationViewModel.Factory

    private var cityAdapter: CityAdapter? = null

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

        viewModel =
            ViewModelProvider(this, vmFactory)[GeolocationViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cities.collect { cities ->
                if (cityAdapter == null) {
                    cityAdapter = CityAdapter(requireContext(), cities)
                    binding.auctvGeolocation.setAdapter(cityAdapter)
                } else {
                    cityAdapter?.clear()
                    cityAdapter?.addAll(cities)
                    cityAdapter?.notifyDataSetChanged()
                }
            }
        }

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        binding.btnResume.setOnClickListener {
            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)
            activityViewModel.increaseProgress()
        }

        binding.skipped.setOnClickListener {
            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)
            activityViewModel.increaseProgress()
        }
    }

}