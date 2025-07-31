package com.kednections.di.modules

import com.kednections.data.repository.UserRepository
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.usecase.user.SetFlagIsFirstPrefUseCase
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
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

    @Singleton
    @Provides
    fun provideLoginUserApiUseCase(
        userRepository: IUserRepository,
    ): LoginUserApiUseCase {
        return LoginUserApiUseCase(
            userRepository = userRepository,
        )
    }

    @Singleton
    @Provides
    fun provideRegisterUserApiUseCase(
        userRepository: IUserRepository,
    ): RegisterUserApiUseCase {
        return RegisterUserApiUseCase(
            userRepository = userRepository,
        )
    }

}