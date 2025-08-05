package com.kednections.di.modules

import com.kednections.domain.irepository.IGeoRepository
import com.kednections.domain.irepository.ISpecializationRepository
import com.kednections.domain.irepository.ITagRepository
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import com.kednections.domain.usecase.specialization.GetSpecializationApiUseCase
import com.kednections.domain.usecase.tag.GetTagsApiUseCase
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
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