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
    private lateinit var validatorSwitcher: AuthValidator

    @Inject
    lateinit var vmFactory: AuthViewModel.Factory

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, vmFactory)[AuthViewModel::class.java]

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isIt.collect { isIt ->
                if (isIt.first) {
                    showSnackbarLong("Этот пользователь уже зарегистрирован.")
                    binding.etEmail.requestFocus()
                } else {
                    if (isIt.second) {
                        when (validatorSwitcher.getGelectedInt()) {
                            0 -> {
                                viewModel.login(
                                    User(
                                        username = viewModel.login.value,
                                        password = viewModel.pass.value,
                                    )
                                )
                            }

                            1 -> {
                                activityViewModel.updateData {
                                    it.copy(
                                        username = viewModel.login.value,
                                        password = viewModel.pass.value,
                                    )
                                }
                                findNavController().navigate(R.id.action_authFragment_to_nickNameFragment)
                            }

                            else -> {
                                //*** переключатель null или цифра выше 1
                                showSnackbarLong("Выберите тип авторизации")
                            }
                        }
                    } else {
                        showSnackbarLong("Ошибка сервера. Попробуйте ещё раз.")
                    }
                }
            }
        }

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        validatorSwitcher = AuthValidator(
            field1 = binding.etEmail,
            field2 = binding.etPassword,
            image1 = binding.logInSwitcher,
            image2 = binding.regSwitcher,
            actionButton = binding.btnResume,
            length1 = 1,
            length2 = 1,
            image1SelectedTop = R.drawable.ic_login_switcher_selected_top,
            image1SelectedBottom = R.drawable.ic_login_switcher_selected_bottom,
            image2SelectedTop = R.drawable.ic_reg_switcher_selected_top,
            image2SelectedBottom = R.drawable.ic_reg_switcher_selected_bottom
        )
        validatorSwitcher.attach()

        binding.btnResume.setOnClickListener {

            val email = validatorSwitcher.getEmail()
            val pass = validatorSwitcher.getPassword()

            if (email.isEmpty()) {
                showSnackbarLong("Заполните поле email.")
                binding.etEmail.requestFocus()
            } else if (!validatorSwitcher.validateEmail()) {
                showSnackbarLong("Введите корректный email адрес.")
                binding.etEmail.requestFocus()
            } else if (pass.isEmpty()) {
                showSnackbarLong("Заполните поле пароля.")
                binding.etPassword.requestFocus()
            } else if (!validatorSwitcher.validatePassword()) {
                if (pass.length < AuthValidator.MIN_PASSWORD_LENGTH) {
                    showSnackbarLong("Пароль должен содержать минимум 6 знаков.")
                } else if (pass.length > AuthValidator.MAX_PASSWORD_LENGTH) {
                    showSnackbarLong("Пароль слишком длинный.")
                } else {
                    showSnackbarLong("Пароль должен содержать хотя бы одну букву и цифру.")
                }
                binding.etPassword.requestFocus()
            } else {
                viewModel.setLoginPass(email, pass, validatorSwitcher)
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