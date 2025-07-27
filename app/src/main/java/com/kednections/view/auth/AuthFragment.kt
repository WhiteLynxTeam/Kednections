package com.kednections.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kednections.databinding.FragmentAuthBinding
import com.kednections.utils.FormValidator
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivity
import dagger.android.support.AndroidSupportInjection
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
            startActivity(Intent(requireActivity(), FormActivity::class.java))
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
    }
}