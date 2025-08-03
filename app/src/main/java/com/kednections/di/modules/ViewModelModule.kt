package com.kednections.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kednections.view.auth.AuthViewModel
import com.kednections.view.form.welcome.WelcomeViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    // Привязка для WelcomeViewModel
    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel

    // Привязка для AuthViewModel
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}