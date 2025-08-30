package com.kednections.di.modules

import com.kednections.domain.irepository.IGeoRepository
import com.kednections.domain.irepository.IPhotoRepository
import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.irepository.ISpecializationRepository
import com.kednections.domain.irepository.ITagRepository
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.istorage.ITokenStorage
import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import com.kednections.domain.usecase.photo.GetPhotoApiUseCase
import com.kednections.domain.usecase.showcase.GetAllShowCaseApiUseCase
import com.kednections.domain.usecase.showcase.UploadListPhotoShowCaseApiUseCase
import com.kednections.domain.usecase.showcase.UploadPhotoShowCaseApiUseCase
import com.kednections.domain.usecase.specialization.GetSpecializationApiUseCase
import com.kednections.domain.usecase.tag.GetTagsApiUseCase
import com.kednections.domain.usecase.token.GetTokenPrefUseCase
import com.kednections.domain.usecase.token.SaveTokenPrefUseCase
import com.kednections.domain.usecase.user.CheckUserApiUseCase
import com.kednections.domain.usecase.user.GetCommMethodApiUseCase
import com.kednections.domain.usecase.user.GetIsFirstRunUseCase
import com.kednections.domain.usecase.user.GetUserApiUseCase
import com.kednections.domain.usecase.user.IsQuestionnaireCompletedUseCase
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
import com.kednections.domain.usecase.user.SaveUserDataPrefUseCase
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
    fun provideCheckUserApiUseCase(
        userRepository: IUserRepository,
    ): CheckUserApiUseCase {
        return CheckUserApiUseCase(
            userRepository = userRepository,
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
    fun provideGetCommMethodApiUseCase(
        userRepository: IUserRepository,
    ): GetCommMethodApiUseCase {
        return GetCommMethodApiUseCase(
            userRepository = userRepository,
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

    @Singleton
    @Provides
    fun provideGetUserApiUseCase(
        userRepository: IUserRepository,
        getTokenPrefUseCase: GetTokenPrefUseCase,
        getCitiesApiUseCase: GetCitiesApiUseCase,
        getSpecializationApiUseCase: GetSpecializationApiUseCase,
        getTagsApiUseCase: GetTagsApiUseCase,
        getCommMethodApiUseCase: GetCommMethodApiUseCase,
        getPhotoApiUseCase: GetPhotoApiUseCase,
    ): GetUserApiUseCase {
        return GetUserApiUseCase(
            userRepository = userRepository,
            getTokenPrefUseCase = getTokenPrefUseCase,
            getCitiesApiUseCase = getCitiesApiUseCase,
            getSpecializationApiUseCase = getSpecializationApiUseCase,
            getTagsApiUseCase = getTagsApiUseCase,
            getCommMethodApiUseCase = getCommMethodApiUseCase,
            getPhotoApiUseCase = getPhotoApiUseCase,
        )
    }

    @Singleton
    @Provides
    fun provideGetPhotoApiUseCase(
        photoRepository: IPhotoRepository,
        getTokenPrefUseCase: GetTokenPrefUseCase
    ): GetPhotoApiUseCase {
        return GetPhotoApiUseCase(
            photoRepository = photoRepository,
            getTokenPrefUseCase = getTokenPrefUseCase,
        )
    }

    @Provides
    fun provideSaveUserDataPrefUseCase(
        userStorage: IUserStorage
    ): SaveUserDataPrefUseCase {
        return SaveUserDataPrefUseCase(userStorage)
    }

    @Provides
    fun provideUploadPhotoShowCaseApiUseCase(
        showCaseRepository: IShowCaseRepository,
        getTokenPrefUseCase: GetTokenPrefUseCase,
    ): UploadPhotoShowCaseApiUseCase {
        return UploadPhotoShowCaseApiUseCase(
            showCaseRepository = showCaseRepository,
            getTokenPrefUseCase = getTokenPrefUseCase,
        )
    }

    @Provides
    fun provideUploadListPhotoShowCaseApiUseCase(
        uploadPhotoShowCaseApiUseCase: UploadPhotoShowCaseApiUseCase,
    ): UploadListPhotoShowCaseApiUseCase {
        return UploadListPhotoShowCaseApiUseCase(
            uploadPhotoShowCaseApiUseCase = uploadPhotoShowCaseApiUseCase,
        )
    }

    @Provides
    fun provideGetAllShowCaseApiUseCase(
        showCaseRepository: IShowCaseRepository,
        getTokenPrefUseCase: GetTokenPrefUseCase,
        getPhotoApiUseCase: GetPhotoApiUseCase,
    ): GetAllShowCaseApiUseCase {
        return GetAllShowCaseApiUseCase(
            showCaseRepository = showCaseRepository,
            getTokenPrefUseCase = getTokenPrefUseCase,
            getPhotoApiUseCase = getPhotoApiUseCase,
        )
    }
}




