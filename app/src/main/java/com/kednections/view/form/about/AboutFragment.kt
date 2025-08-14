package com.kednections.view.form.about

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentAboutBinding
import com.kednections.utils.startMarquee
import com.kednections.utils.uiextensions.showSnackbarLong
import com.kednections.view.activity.FormActivity
import com.kednections.view.activity.FormActivityViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: AboutViewModel

    @Inject
    lateinit var vmFactory: AboutViewModel.Factory

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAboutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, vmFactory)[AboutViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            activityViewModel.isReg.collect {
                if (it) findNavController().navigate(R.id.action_aboutFragment_to_successRegFragment)
                else {
                    showSnackbarLong("Ошибка регистрации.")
                }
            }
        }

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
            updateRegUser()
            activityViewModel.register()
//            findNavController().navigate(R.id.action_aboutFragment_to_successRegFragment)

        }

        binding.skipped.setOnClickListener {
            updateRegUser()
            activityViewModel.register()
//            findNavController().navigate(R.id.action_aboutFragment_to_successRegFragment)
        }

        (activity as FormActivity).setUIVisibility(
            showHeader = true
        )

    }

    private fun updateRegUser() {
        activityViewModel.updateData {
            it.copy(
                //[red] заглушка для проверки регистрации - передаем второй город
                description = binding.etAbout.text.toString()
            )
        }
    }
}