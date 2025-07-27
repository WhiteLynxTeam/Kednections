package com.kednections.view.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.kednections.R
import com.kednections.databinding.ActivityFormBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    private lateinit var viewModel: FormActivityViewModel

    private val navController by lazy {
        NavHostFragment.findNavController(supportFragmentManager.findFragmentById(R.id.fragment_placeholder) as NavHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[FormActivityViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.progress.collectLatest { value ->
                    binding.progressbar.progress = value
                    binding.tvCount.text = "$value/5"
                }
            }
        }

        binding.imgBack.setOnClickListener {
            handleBackPressed()
        }

            //темные иконки для светлого фона нижнего navigation_bar
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightNavigationBars = true

    }

    fun handleBackPressed() {
        val navController = supportFragmentManager.findFragmentById(R.id.fragment_placeholder)
            ?.let { it as? NavHostFragment }
            ?.navController

        // Проверка, можно ли вернуться назад
        if (navController?.previousBackStackEntry != null) {
            // Уменьшаем прогресс только на определённых экранах
            when (navController.currentDestination?.id) {
                R.id.specializationFragment, -> {
                    viewModel.decreaseProgress()
                }
                R.id.geolocationFragment, -> {
                    viewModel.decreaseProgress()
                }
            }

            navController.popBackStack()
        } else {
            finish()
        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        handleBackPressed()
    }

}