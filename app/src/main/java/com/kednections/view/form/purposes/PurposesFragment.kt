package com.kednections.view.form.purposes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentPurposesBinding
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivityViewModel
import javax.inject.Inject

class PurposesFragment : BaseFragment<FragmentPurposesBinding>() {

    private val activityViewModel: FormActivityViewModel by activityViewModels()

    private lateinit var viewModel: PurposesViewModel


    @Inject
    lateinit var vmFactory: PurposesViewModel.Factory

    // Адаптер для RecyclerView, отображающего список целей
    private lateinit var purposesAdapter: PurposesAdapter

    // Создание и возврат binding для фрагмента
    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPurposesBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this, vmFactory)[PurposesViewModel::class.java]


        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        // Инициализация адаптера для RecyclerView
        purposesAdapter = PurposesAdapter(requireContext()) {
            // Колбэк, вызываемый при изменении состояния выбора целей
            updateResumeButtonState()
        }

        // Настройка RecyclerView
        binding.recyclerViewPurposes.apply {
            // Установка LinearLayoutManager для вертикального списка
            layoutManager = LinearLayoutManager(requireContext())
            // Установка адаптера
            adapter = purposesAdapter
            // Оптимизация - все элементы имеют одинаковый размер
            setHasFixedSize(true)
        }

        // Обработчик клика по кнопке "Продолжить"
        binding.btnResume.setOnClickListener {
            // Обновление данных в активности формы
            activityViewModel.updateData {
                // Копируем текущие данные, обновляя список тегов
                it.copy(
                    // Берем первые два тега из ViewModel (заглушка для проверки)
                    tags = viewModel.tags.value.take(2)
                )
            }
            // Переход к следующему фрагменту
            findNavController().navigate(R.id.action_purposesFragment_to_chooseCommunicateFragment)
        }

        // Инициализация состояния кнопки при создании фрагмента
        updateResumeButtonState()
    }

    // Обновление состояния кнопки "Продолжить" в зависимости от выбора целей
    private fun updateResumeButtonState() {
        // Активируем кнопку только если есть выбранные цели
        binding.btnResume.isEnabled = purposesAdapter.hasCheckedItems()
    }
}