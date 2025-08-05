package com.kednections.di.modules

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.usecase.user.GetIsFirstRunUseCase
import com.kednections.domain.usecase.user.IsQuestionnaireCompletedUseCase
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
import com.kednections.domain.usecase.user.SetFirstRunCompletedUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideSetFirstRunCompletedUseCase(
        storage: IUserStorage,
    ): SetFirstRunCompletedUseCase {
        return SetFirstRunCompletedUseCase(
            storage = storage,
        )
    }

    @Singleton
    @Provides
    fun provideIsQuestionnaireCompletedUseCase(
        storage: IUserStorage,
    ): IsQuestionnaireCompletedUseCase {
        return IsQuestionnaireCompletedUseCase(
            storage = storage,
        )
    }

    @Singleton
    @Provides
    fun provideGetIsFirstRunUseCase(
        storage: IUserStorage,
    ): GetIsFirstRunUseCase {
        return GetIsFirstRunUseCase(
            storage = storage,
        )
    }

//    @Singleton
//    @Provides
//    fun provideGetCurrentUserUseCase(
//        userRepository: IUserRepository,
//    ): GetCurrentUserUseCase {
//        return GetCurrentUserUseCase(
//            userRepository = userRepository,
//        )
//    }


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