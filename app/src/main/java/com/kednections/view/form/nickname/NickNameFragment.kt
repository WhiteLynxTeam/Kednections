package com.kednections.view.form.nickname

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentNickNameBinding
import com.kednections.utils.FormValidator
import com.kednections.utils.SwitcherValidator
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivity
import dagger.android.support.AndroidSupportInjection

class NickNameFragment : Fragment() {

    private var _binding: FragmentNickNameBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNickNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        //функция смены цвета фона и текста для кнопки "продолжить"
        val validator = FormValidator(
            field1 = binding.etName,
            field2 = binding.etNick,
            actionButton = binding.btnResume,
            length2 = 1
        )
        validator.attach()

        val validatorSwitcher = SwitcherValidator(
            field1 = binding.etName,
            field2 = binding.etNick,
            image1 = binding.nameSwitcher,
            image2 = binding.nickSwitcher,
            actionButton = binding.btnResume,
            length1 = 1,
            length2 = 1,
            switcherBorder = binding.switcherBorder
        )
        validatorSwitcher.attach()

        binding.btnResume.setOnClickListener {
            findNavController().navigate(R.id.action_nickNameFragment_to_avatarFragment)
        }

        (activity as FormActivity).setUIVisibility(
            showHeader = true
        )
    }

}

