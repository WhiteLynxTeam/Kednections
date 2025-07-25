package com.kednections.view.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kednections.R
import com.kednections.databinding.FragmentWelcomeBinding
import com.kednections.utils.AppPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var appPreferences: AppPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        appPreferences = AppPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            // Задержка для показа welcome-экрана (3 секунды)
            delay(3000)

            checkUserStateAndNavigate()
        }
    }

    private fun checkUserStateAndNavigate() {
        val currentUser = auth.currentUser
        val isFirstRun = appPreferences.isFirstRun

        if (currentUser != null && !isFirstRun) {
            // Пользователь уже авторизован и это не первый запуск
            // Здесь должна быть навигация на главный экран приложения
            // Временно переходим на authFragment
            navigateToAuth()
        } else {
            // Новый пользователь или сброс приложения
            navigateToAuth()
            appPreferences.isFirstRun = false
        }
    }

    private fun navigateToAuth() {
        findNavController().navigate(R.id.action_welcome_to_auth)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}