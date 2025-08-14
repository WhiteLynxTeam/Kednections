package com.kednections.view.form.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.core.base.BaseFragment
import com.kednections.databinding.FragmentWelcomeBinding
import com.kednections.view.activity.FormActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    @Inject
    lateinit var viewModelFactory: WelcomeViewModel.Factory

    private val viewModel: WelcomeViewModel by viewModels { viewModelFactory }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWelcomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Скрываем заголовок активности (если нужно)
        (activity as FormActivity).setUIVisibility(showHeader = false)

        binding.root.alpha = 0f
        binding.root.scaleX = 0.9f
        binding.root.scaleY = 0.9f

        // Запуск анимации
        binding.root.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // Запускаем корутину в рамках жизненного цикла фрагмента
        lifecycleScope.launch {
            // Собираем события навигации из ViewModel
            viewModel.navigationEvent.collect { destination ->
                when (destination) {
                    WelcomeViewModel.NavigationDestination.Auth -> navigateToAuth()
                    WelcomeViewModel.NavigationDestination.Main -> navigateToMain()
                }
            }
        }

        // Через 3 секунды вызываем проверку состояния пользователя
        lifecycleScope.launch {
            delay(2000)
            viewModel.checkUserStateAndNavigate()
        }
    }


    // Переход на экран авторизации
    private fun navigateToAuth() {
        findNavController().navigate(R.id.action_welcomeFragment_to_authFragment)
    }

    // Переход на главный экран приложения
    private fun navigateToMain() {
        findNavController().navigate(R.id.feedFragment)
    }
}