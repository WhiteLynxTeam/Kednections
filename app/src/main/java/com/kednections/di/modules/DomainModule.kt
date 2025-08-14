package com.kednections.di.modules

import com.kednections.domain.irepository.IGeoRepository
import com.kednections.domain.irepository.ISpecializationRepository
import com.kednections.domain.irepository.ITagRepository
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.istorage.ITokenStorage
import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import com.kednections.domain.usecase.specialization.GetSpecializationApiUseCase
import com.kednections.domain.usecase.tag.GetTagsApiUseCase
import com.kednections.domain.usecase.token.GetTokenPrefUseCase
import com.kednections.domain.usecase.token.SaveTokenPrefUseCase
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
    fun provideGetTokenPrefUseCase(
        tokenStorage: ITokenStorage,
    ): GetTokenPrefUseCase {
        return GetTokenPrefUseCase(
            tokenStorage = tokenStorage,
        )
    }

    @Singleton
    @Provides
    fun provideSaveTokenPrefUseCase(
        tokenStorage: ITokenStorage,
    ): SaveTokenPrefUseCase {
        return SaveTokenPrefUseCase(
            tokenStorage = tokenStorage,
        )
    }

    @Singleton
    @Provides
    fun provideLoginUserApiUseCase(
        userRepository: IUserRepository,
        saveTokenPrefUseCase: SaveTokenPrefUseCase,
    ): LoginUserApiUseCase {
        return LoginUserApiUseCase(
            userRepository = userRepository,
            saveTokenPrefUseCase = saveTokenPrefUseCase,
        )
    }

    @Singleton
    @Provides
    fun provideRegisterUserApiUseCase(
        userRepository: IUserRepository,
        saveTokenPrefUseCase: SaveTokenPrefUseCase,
    ): RegisterUserApiUseCase {
        return RegisterUserApiUseCase(
            userRepository = userRepository,
            saveTokenPrefUseCase = saveTokenPrefUseCase,
        )
    }

    @Singleton
    @Provides
    fun provideGetCitiesApiUseCase(
        geoRepository: IGeoRepository,
    ): GetCitiesApiUseCase {
        return GetCitiesApiUseCase(
            geoRepository = geoRepository,
        )
    }

    @Singleton
    @Provides
    fun provideGetTagsApiUseCase(
        tagRepository: ITagRepository,
    ): GetTagsApiUseCase {
        return GetTagsApiUseCase(
            tagRepository = tagRepository,
        )
    }

    @Singleton
    @Provides
    fun provideGetSpecializationApiUseCase(
        specializationRepository: ISpecializationRepository,
    ): GetSpecializationApiUseCase {
        return GetSpecializationApiUseCase(
            specializationRepository = specializationRepository,
        )
    }

}