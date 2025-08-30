package com.kednections.view.form.choose_communicate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentChooseCommunicateBinding
import com.kednections.domain.models.Communication
import com.kednections.view.activity.FormActivityViewModel
import javax.inject.Inject


class ChooseCommunicateFragment : BaseFragment<FragmentChooseCommunicateBinding>() {
    //[yellow] Переделать чекбоксы, чтобы можно было определить что нажимается для отправки на сервер
    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: ChooseCommunicateViewModel

    @Inject
    lateinit var vmFactory: ChooseCommunicateViewModel.Factory

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentChooseCommunicateBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, vmFactory)[ChooseCommunicateViewModel::class.java]

        val items = listOf(
            RadioButtonItem(
                view = binding.viewOnline,
                checkbox = binding.checkboxOnline,
                imageView = binding.ivOnline,
                selectedIcon = R.drawable.ic_online_selected,
                unselectedIcon = R.drawable.ic_online,
                name = "online",
            ),
            RadioButtonItem(
                view = binding.viewOffline,
                checkbox = binding.checkboxOffline,
                imageView = binding.ivOffline,
                selectedIcon = R.drawable.ic_offline_selected,
                unselectedIcon = R.drawable.ic_offline,
                name = "offline",
            )
        )

        // Устанавливаем слушатели
        items.forEach { item ->
            // обработка нажатия на сам чекбокс
            item.checkbox.setOnCheckedChangeListener { _, isChecked ->

                //[yellow] самый простой тупой вариант, переделать
                // потеряли переключение для вывбора коммуникации
                // для чего мы это делаем - потому что сервер ничего путного не возвращает
                if (isChecked) {
                    if (item.name == "online") {
                        Communication.ONLINE.isCheck = true
                        Communication.OFFLINE.isCheck = false
                    } else if (item.name == "offline") {
                        Communication.ONLINE.isCheck = false
                        Communication.OFFLINE.isCheck = true
                    } else {
                        Communication.ONLINE.isCheck = false
                        Communication.OFFLINE.isCheck = false
                    }
                }

                handleSingleSelection(items, item, isChecked)
            }

            // обработка нажатия на весь контейнер (view)
            item.view.setOnClickListener {
                // ставим галочку на выбранный
                if (!item.checkbox.isChecked) {
                    item.checkbox.isChecked = true
                } else {
                    // если хочешь запретить снятие выбора — оставь пусто
                    // или разреши переключать
                    item.checkbox.isChecked = false
                }
            }
        }
//        items.forEach { item ->
//            item.checkbox.setOnCheckedChangeListener { _, isChecked ->
//                //[yellow] самый простой тупой вариант, переделать
//                if (isChecked) {
//                    if (item.name == "online") {
//                        Communication.ONLINE.isCheck = true
//                        Communication.OFFLINE.isCheck = false
//                    } else if (item.name == "offline") {
//                        Communication.ONLINE.isCheck = false
//                        Communication.OFFLINE.isCheck = true
//                    } else {
//                        Communication.ONLINE.isCheck = false
//                        Communication.OFFLINE.isCheck = false
//                    }
//                }
//                handleSingleSelection(items, item, isChecked)
//            }
//        }

        binding.btnResume.setOnClickListener {
            val uuid = Communication.getCheckedUuid()
            if (uuid != null) {
                activityViewModel.updateData {
                    it.copy(communicationMethod = uuid)
                }
            }
            findNavController().navigate(R.id.action_chooseCommunicateFragment_to_aboutFragment)
            activityViewModel.increaseProgress()
        }
    }

    private fun handleSingleSelection(
        allItems: List<RadioButtonItem>,
        selectedItem: RadioButtonItem,
        isChecked: Boolean
    ) {
        if (isChecked) {
            // снимаем выбор со всех остальных
            allItems.filter { it != selectedItem }.forEach { item ->
                item.checkbox.isChecked = false
                item.view.setBackgroundResource(R.drawable.bg_auth_input)
                item.imageView.setImageResource(item.unselectedIcon)
            }
        }

        // обновляем UI текущего элемента
        selectedItem.view.setBackgroundResource(
            if (isChecked) R.drawable.bg_view_purpose_fragment else R.drawable.bg_auth_input
        )
        selectedItem.imageView.setImageResource(
            if (isChecked) selectedItem.selectedIcon else selectedItem.unselectedIcon
        )

        // обновляем кнопку
        updateResumeButtonState()
    }


//    private fun handleSingleSelection(
//        allItems: List<RadioButtonItem>,
//        selectedItem: RadioButtonItem,
//        isChecked: Boolean
//    ) {
//        if (isChecked) {
//            // Снимаем выделение со всех остальных
//            allItems.filter { it != selectedItem }.forEach { item ->
//                item.checkbox.setOnCheckedChangeListener(null)
//                item.checkbox.isChecked = false
//                item.view.setBackgroundResource(R.drawable.bg_auth_input)
//                item.imageView.setImageResource(item.unselectedIcon)
//                item.checkbox.setOnCheckedChangeListener { _, isCheckedNew ->
//                    handleSingleSelection(allItems, item, isCheckedNew)
//                }
//            }
//        }
//
//        // Обновляем UI текущего элемента
//        selectedItem.view.setBackgroundResource(
//            if (isChecked) R.drawable.bg_view_purpose_fragment else R.drawable.bg_auth_input
//        )
//        selectedItem.imageView.setImageResource(
//            if (isChecked) selectedItem.selectedIcon else selectedItem.unselectedIcon
//        )
//
//        // Обновляем кнопку
//        updateResumeButtonState()
//    }


    private fun updateResumeButtonState() {
        val isAnyChecked = listOf(
            binding.checkboxOnline.isChecked,
            binding.checkboxOffline.isChecked
        ).any { it }

        binding.btnResume.isEnabled = isAnyChecked
    }

    data class RadioButtonItem(
        val view: View,
        val checkbox: android.widget.RadioButton,
        val imageView: android.widget.ImageView,
        val selectedIcon: Int,
        val unselectedIcon: Int,
        //[yellow] тупо по быстрому вставим название кнопки, чтобы знать какая нажата
        //потом подправим
        val name: String,
    )
}