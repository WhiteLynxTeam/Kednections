package com.kednections.di

import android.content.Context
import com.kednections.App
import com.kednections.di.modules.AuthModule
import com.kednections.di.modules.AppModule
import com.kednections.di.modules.DataModule
import com.kednections.di.modules.DomainModule
import com.kednections.di.modules.MainModule
import com.kednections.di.modules.RemoteModule
import com.kednections.di.modules.SharedPreferencesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        AuthModule::class,
        DataModule::class,
        DomainModule::class,
        MainModule::class,
        RemoteModule::class,
        SharedPreferencesModule::class,
    ]
)

interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun withContext(context: Context): Builder
        fun build(): AppComponent
    }
}

