package com.kednections.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = viewModels[modelClass]
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        return provider.get() as T
    }
}

@Module
class ViewModelFactoryModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(viewModels)
    }
}