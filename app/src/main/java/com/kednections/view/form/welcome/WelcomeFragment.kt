package com.kednections.view.form.welcome

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
import com.kednections.view.activity.FormActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    //[yellow] FirebaseAuth перенести во вьюмодель данного фрагмента, подтягивается через di
    private lateinit var auth: FirebaseAuth
    //[yellow] Эту удалить. Работаем по чистой архитектуре со слоями, вместо прямой ссылки на
    //sharedPreferences работаем с помощью usecase и repository, storage
    private lateinit var appPreferences: AppPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Обязательно требуется для di:
        AndroidSupportInjection.inject(this)
        //Не забываем добавлять фрагмент в MainModule. Без этого будет краш по di
        //На будущее, сейчас я добавил

        /* Должны быть во вьюмодели.*/
        auth = FirebaseAuth.getInstance()
        //вместо этого usecase, которые используем во фрагменте
        appPreferences = AppPreferences(requireContext())
        /**/
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

        /*[green] Добавляем переменную вьюмодель, см. аналогию в др. фрагментах*/

        lifecycleScope.launch {
            // Задержка для показа welcome-экрана (3 секунды)
            delay(3000)

            checkUserStateAndNavigate()
        }

        (activity as FormActivity).setUIVisibility(
            showHeader = false
        )

    }


    private fun checkUserStateAndNavigate() {
        /*[green] логика должна быть во вьюмодели. Эти переменные будут во вьюмодели*/
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
        findNavController().navigate(R.id.action_welcomeFragment_to_authFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
