package com.kednections.view.form.nickname

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentNickNameBinding
import com.kednections.utils.NickNameValidator
import com.kednections.utils.startMarquee
import com.kednections.view.activity.FormActivity
import com.kednections.view.activity.FormActivityViewModel
import com.kednections.view.auth.AuthViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class NickNameFragment : Fragment() {

    private var _binding: FragmentNickNameBinding? = null
    private val binding get() = _binding!!

    private val activityViewModel: FormActivityViewModel by activityViewModels()
    private lateinit var viewModel: NickNameViewModel

    @Inject
    lateinit var vmFactory: NickNameViewModel.Factory

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

        viewModel =
            ViewModelProvider(this, vmFactory)[NickNameViewModel::class.java]

        //бегущая строка (Анимация)
        startMarquee(binding.textDescription, binding.textHorizontalScroll, speed = 5000L)

        val validatorSwitcher = NickNameValidator(
            field1 = binding.etName,
            field2 = binding.etNick,
            image1 = binding.nameSwitcher,
            image2 = binding.nickSwitcher,
            actionButton = binding.btnResume,
            length1 = 1,
            length2 = 1,
            switcherBorder = binding.switcherBorder,
            image1Top = R.drawable.ic_name_switcher_top,
            image1Bottom = R.drawable.ic_name_switcher_bottom,
            image1SelectedTop = R.drawable.ic_name_switcher_selected_top,
            image1SelectedBottom = R.drawable.ic_name_switcher_selected_bottom,
            image2Bottom = R.drawable.ic_nick_switcher_bottom,
            image2SelectedTop = R.drawable.ic_nick_switcher_selected_top,
            image2SelectedBottom = R.drawable.ic_nick_switcher_selected_bottom
        )
        validatorSwitcher.attach()

        binding.btnResume.setOnClickListener {
            //[yellow] отсутствует поля в модели регистрации юзера на сервере
            //[yellow] флаг, что выбрали - ник или фио
            activityViewModel.updateData {
                it.copy(
                    fio = binding.etName.text.toString(),
                    nick = binding.etNick.text.toString(),
                    fioOrNick = validatorSwitcher.getSelected() ?: "NAME"
                )
            }
            findNavController().navigate(R.id.action_nickNameFragment_to_avatarFragment)
        }

        (activity as FormActivity).setUIVisibility(
            showHeader = true
        )
    }

}

