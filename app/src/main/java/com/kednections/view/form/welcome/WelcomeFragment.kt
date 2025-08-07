package com.kednections.view.form.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kednections.R
import com.kednections.databinding.FragmentWelcomeBinding
import com.kednections.view.activity.FormActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var viewModelFactory: WelcomeViewModel.Factory


    private val viewModel: WelcomeViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Внедрение зависимостей во фрагмент с помощью Dagger
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инициализация binding
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Скрываем заголовок активности (если нужно)
        (activity as FormActivity).setUIVisibility(showHeader = false)

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
            delay(3000)
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

    override fun onDestroyView() {
        super.onDestroyView()
        // Очищаем binding при уничтожении View
        _binding = null
    }
}