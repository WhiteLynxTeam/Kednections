package com.kednections.view.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kednections.R
import com.kednections.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var activityViewModel: MainActivityViewModel

    private val navController by lazy {
        NavHostFragment.findNavController(supportFragmentManager.findFragmentById(R.id.fragment_placeholder_activity_main) as NavHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityViewModel =
            ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding.bottomNavigation.selectedItemId = R.id.feed
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.elevation = 20f

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.acquaintances -> {
                    navController.navigate(R.id.acquaintancesFragment)
                    updateBottomNavColors(
                        iconColorRes = R.color.bottom_nav_color,
                        textColorRes = R.color.bottom_nav_color,
                        backgroundRes = R.drawable.bottom_nav_background
                    )
                    true
                }

                R.id.feed -> {
                    navController.navigate(R.id.feedFragment)
                    updateBottomNavColors(
                        iconColorRes = R.color.bottom_nav_color,
                        textColorRes = R.color.bottom_nav_color,
                        backgroundRes = R.drawable.bottom_nav_background
                    )
                    true
                }

                R.id.communication -> {
                    navController.navigate(R.id.communicationFragment)
                    updateBottomNavColors(
                        iconColorRes = R.color.bottom_nav_color,
                        textColorRes = R.color.bottom_nav_color,
                        backgroundRes = R.drawable.bottom_nav_background
                    )
                    true
                }

                R.id.profile -> {
                    activityViewModel.setIsProfileTop(true)
                    navController.navigate(R.id.profileFragment)
                    updateBottomNavColors(
                        iconColorRes = R.color.bottom_nav_color_profile,
                        textColorRes = R.color.bottom_nav_color_profile,
                        backgroundRes = R.color.orange
                    )
                    true
                }

                else -> false
            }
        }

        //темные иконки для светлого фона нижнего navigation_bar
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightNavigationBars = true
    }

    fun setUIVisibility(showBottom: Boolean) {
        binding.bottomNavigation.visibility = if (showBottom) View.VISIBLE else View.GONE
    }

    private fun updateBottomNavColors(
        @ColorRes iconColorRes: Int,
        @ColorRes textColorRes: Int,
        @DrawableRes backgroundRes: Int
    ) {
        binding.bottomNavigation.apply {
            itemIconTintList = ContextCompat.getColorStateList(context, iconColorRes)
            itemTextColor = ContextCompat.getColorStateList(context, textColorRes)
            setBackgroundResource(backgroundRes)
        }
    }
}