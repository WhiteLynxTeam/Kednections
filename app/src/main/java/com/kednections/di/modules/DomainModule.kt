package com.kednections.di.modules

import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.usecase.user.SetFlagIsFirstPrefUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideSetFlagIsFirstPrefUseCase(
        storage: IUserStorage,
    ): SetFlagIsFirstPrefUseCase {
        return SetFlagIsFirstPrefUseCase(
            storage = storage,
        )
    }

}