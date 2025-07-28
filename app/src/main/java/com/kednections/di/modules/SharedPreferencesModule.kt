package com.kednections.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        const val SHARED_PREFERENCES = "app_prefs"
    }
}