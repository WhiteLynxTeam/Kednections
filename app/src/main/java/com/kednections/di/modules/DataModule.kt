package com.kednections.di.modules

import android.content.SharedPreferences
import com.kednections.data.storage.UserStorage
import com.kednections.domain.istorage.IUserStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provideUserStorage(
        sharedPreferences: SharedPreferences,
    ): IUserStorage {
        return UserStorage(
            sharedPreferences = sharedPreferences,
        )
    }
}