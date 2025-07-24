package com.kednections.view.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kednections.databinding.FragmentAuthBinding
import com.kednections.utils.startMarquee
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

        val textView = binding.textDescription
        val container = binding.textHorizontalScroll

        //бегущая строка (Анимация)
        startMarquee(textView, container, speed = 5000L)

        //функция смены цвета фона и текста для кнопки "продолжить"
        fun updateButtonState() {
            val isEdit1Valid = (binding.etEmail.text?.length ?: 0) >= 1
            val isEdit2Valid = (binding.etPassword.text?.length ?: 0) >= 8

            binding.btnResume.isEnabled = isEdit1Valid && isEdit2Valid
        }

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = updateButtonState()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        //следит за заполнением полей EditText
        binding.etEmail.addTextChangedListener(watcher)
        binding.etPassword.addTextChangedListener(watcher)

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