package com.kednections.view.form.geolocation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentGeolocationBinding
import com.kednections.utils.startMarquee
import com.kednections.utils.uiextensions.showSnackbarLong
import com.kednections.view.activity.FormActivityViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GeolocationFragment : BaseFragment<FragmentGeolocationBinding>() {
    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: GeolocationViewModel

    @Inject
    lateinit var vmFactory: GeolocationViewModel.Factory

    private lateinit var cityAdapter: ArrayAdapter<String>
    //[yellow] с кастомным адаптером надо еще фильтрацию реализовывать. отложим пока
    //поработаем со стандартным
//    private val cityAdapter by lazy { CityAdapter(requireContext(), mutableListOf()) }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGeolocationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, vmFactory)[GeolocationViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cities.collect { cities ->
                cityAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    cities.map { it.name }
                )
                binding.auctvGeolocation.setAdapter(cityAdapter)
            }
        }

        binding.auctvGeolocation.setOnItemClickListener { parent, view, position, id ->
            // Активируем кнопку
            binding.btnResume.isEnabled = true
        }

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        binding.btnResume.setOnClickListener {

            val cityName = binding.auctvGeolocation.text.toString().trim()
            val selectedCity = viewModel.cities.value.find { it.name == cityName }
            if (selectedCity != null) {
                activityViewModel.updateData {
                    it.copy(
                        city = selectedCity
                    )
                }
            } else {
                showSnackbarLong("Выберите город из списка.")
            }
            activityViewModel.increaseProgress()
            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)

        }

        binding.skipped.setOnClickListener {

            // [green] Дублироание кода, перенести логику во вьюмодель
            val defaultCityName = getString(R.string.city_default)
            val selectedCity = viewModel.cities.value.firstOrNull {
                it.name.contains(
                    defaultCityName,
                    ignoreCase = true
                )
            }
            if (selectedCity != null) {
                activityViewModel.updateData {
                    it.copy(
                        city = selectedCity
                    )
                }
            } else {
                println("Error!!! Изменено название дефолтного города!!!")
            }

            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)
            activityViewModel.increaseProgress()
        }
    }
}