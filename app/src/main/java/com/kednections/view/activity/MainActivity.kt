package com.kednections.view.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kednections.R
import com.kednections.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        NavHostFragment.findNavController(supportFragmentManager.findFragmentById(R.id.fragment_placeholder_activity_main) as NavHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.selectedItemId = R.id.feed
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.elevation = 20f

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.acquaintances -> {
                    navController.navigate(R.id.acquaintancesFragment)
                    true
                }

                R.id.feed -> {
                    navController.navigate(R.id.feedFragment)
                    true
                }

                R.id.communication -> {
                    navController.navigate(R.id.communicationFragment)
                    true
                }

                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }

                else -> false
            }
        }
    }

    fun setUIVisibility(showHeader: Boolean) {
        binding.bottomNavigation.visibility = if (showHeader) View.VISIBLE else View.GONE
    }
}