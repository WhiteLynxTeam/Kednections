package com.kednections.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentAuthBinding
import com.kednections.domain.models.user.User
import com.kednections.utils.AuthValidator
import com.kednections.utils.startMarquee
import com.kednections.utils.uiextensions.showSnackbarLong
import com.kednections.view.activity.FormActivity
import com.kednections.view.activity.FormActivityViewModel
import com.kednections.view.activity.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var vmFactory: AuthViewModel.Factory

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, vmFactory)[AuthViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isRegistry.collect {
                if (it) findNavController().navigate(R.id.action_authFragment_to_nickNameFragment)
                else {
                    showSnackbarLong("Ошибка регистрации.")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLogin.collect {
                if (it) {
                    lifecycleScope.launch {
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    }
//                    showSnackbarLong("Вы авторизировались.")
                } else {
                    showSnackbarLong("Ошибка авторизации.")
                }
            }
        }

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        val validatorSwitcher = AuthValidator(
            field1 = binding.etEmail,
            field2 = binding.etPassword,
            image1 = binding.logInSwitcher,
            image2 = binding.regSwitcher,
            actionButton = binding.btnResume,
            length1 = 1,
            length2 = 1,
            switcherBorder = binding.switcherBorder,
            image1Top = R.drawable.ic_login_switcher_top,
            image1Bottom = R.drawable.ic_login_switcher_bottom,
            image1SelectedTop = R.drawable.ic_login_switcher_selected_top,
            image1SelectedBottom = R.drawable.ic_login_switcher_selected_bottom,
            image2Bottom = R.drawable.ic_reg_switcher_bottom,
            image2SelectedTop = R.drawable.ic_reg_switcher_selected_top,
            image2SelectedBottom = R.drawable.ic_reg_switcher_selected_bottom
        )
        validatorSwitcher.attach()

        binding.btnResume.setOnClickListener {
            //[green] паттерны Regex перенести в константы в утилиты, потому что еще почту надо
            //на корректность проверить
            //[green] и проверку по Regex также можно перенести в утилиты
            val regex = Regex("^(?=.*[A-Za-zА-Яа-я])(?=.*\\d).+$")
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            //[yellow] Сделать проверку на валидность email
            //[green] посмотреть какое ограничение на максимальное количестов символов для полей
            if (email.isEmpty()) {
                showSnackbarLong("Заполните поля.")
                binding.etEmail.requestFocus()
            } else if (pass.isEmpty()) {
                showSnackbarLong("Заполните поля.")
                binding.etPassword.requestFocus()
            } else if (pass.length < 6) {
                showSnackbarLong("Пароль должен содержать минимум 6 знаков.")
                binding.etPassword.requestFocus()
            } else if (!regex.matches(pass)) {
                showSnackbarLong("Пароль должен содержать хотя бы одну букву и цифру.")
                binding.etPassword.requestFocus()
            } else {
                when (validatorSwitcher.getGelectedInt()) {
                    0 -> {
                        viewModel.login(
                            User(
                                username = binding.etEmail.text.toString(),
                                password = binding.etPassword.text.toString(),
                            )
                        )
                    }

                    1 -> {
                        activityViewModel.updateData {
                            it.copy(
                                username = binding.etEmail.text.toString(),
                                password = binding.etPassword.text.toString(),
                            )
                        }
                        findNavController().navigate(R.id.action_authFragment_to_nickNameFragment)
                    }

                    else -> {
                        //*** переключатель null или цифра выше 1
                    }
                }
            }
        }

        binding.icGoogle.setOnClickListener {
            viewModel.signInWithGoogle(
                context = requireContext(),
                onSuccess = { user ->
                    if (user != null) {
                        user.email?.let { email ->
                            Snackbar.make(
                                binding.root,
                                email,
                                Snackbar.LENGTH_INDEFINITE
                            ).setAction("Done") {

                            }.show()
                        }
                    }
                },
                onFailure = { e ->
                    Snackbar.make(
                        binding.root,
                        "ошибка аутентификации",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Done") {

                    }.show()
                },
            )

            return@setOnClickListener
        }

        (activity as FormActivity).setUIVisibility(
            showHeader = false
        )

    }
}