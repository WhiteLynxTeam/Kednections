package com.kednections.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kednections.R
import com.kednections.databinding.FragmentAuthBinding
import com.kednections.domain.models.User
import com.kednections.utils.FormValidator
import com.kednections.utils.startMarquee
import com.kednections.utils.uiextensions.showSnackbarLong
import com.kednections.view.activity.FormActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var vmFactory: AuthViewModel.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, vmFactory)[AuthViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isRegistry.collect {
                if (it) startActivity(Intent(requireActivity(), FormActivity::class.java))
                else {
                    showSnackbarLong("Ошибка регистрации.")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLogin.collect {
                if (it) startActivity(Intent(requireActivity(), FormActivity::class.java))
                else {
                    showSnackbarLong("Ошибка авторизации.")
                }
            }
        }

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        //функция смены цвета фона и текста для кнопки "продолжить"
        val validator = FormValidator(
            field1 = binding.etEmail,
            field2 = binding.etPassword,
            actionButton = binding.btnResume,
            length2 = 1
        )
        validator.attach()

        binding.btnResume.setOnClickListener {
            if (binding.etEmail.text.toString().isEmpty() || binding.etPassword.text.toString()
                    .isEmpty()
            ) {
                showSnackbarLong("Заполните поля.")
                return@setOnClickListener
            }

            viewModel.login(
                User(
                    username = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString(),
                )
            )
//            findNavController().navigate(R.id.action_authFragment_to_nickNameFragment)
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