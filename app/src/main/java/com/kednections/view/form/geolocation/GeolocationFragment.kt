package com.kednections.view.form.geolocation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentGeolocationBinding
import com.kednections.domain.models.City
import com.kednections.utils.startMarquee
import com.kednections.utils.uiextensions.showSnackbarLong
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

    private lateinit var cityAdapter: ArrayAdapter<String>
    //[yellow] с кастомным адаптером надо еще фильтрацию реализовывать. отложим пока
    //поработаем со стандартным
//    private val cityAdapter by lazy { CityAdapter(requireContext(), mutableListOf()) }

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
                cityAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    cities.map { it.name }
                )
                binding.auctvGeolocation.setAdapter(cityAdapter)
            }
        }

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        binding.btnResume.setOnClickListener {
            updateRegUser(binding.auctvGeolocation.text.toString().trim())
            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)
            activityViewModel.increaseProgress()
        }

        binding.skipped.setOnClickListener {
            findNavController().navigate(R.id.action_geolocationFragment_to_purposesFragment)
            activityViewModel.increaseProgress()
        }
    }

    private fun updateRegUser(cityName: String){
        val selectedCity = viewModel.cities.value.find { it.name == cityName }
        if (selectedCity != null) {
            activityViewModel.updateData {
                it.copy(
                    //[red] заглушка для проверки регистрации - передаем второй город
                    city = selectedCity
                )
            }
        } else {
            showSnackbarLong("Выберите город из списка.")
        }

    }
}