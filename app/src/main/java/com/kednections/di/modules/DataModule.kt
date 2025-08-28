package com.kednections.di.modules

import android.content.SharedPreferences
import com.kednections.data.network.api.FileApi
import com.kednections.data.network.api.GeoApi
import com.kednections.data.network.api.ShowCaseApi
import com.kednections.data.network.api.SpecializationsApi
import com.kednections.data.network.api.TagApi
import com.kednections.data.network.api.UserApi
import com.kednections.data.repository.GeoRepository
import com.kednections.data.repository.PhotoRepository
import com.kednections.data.repository.ShowCaseRepository
import com.kednections.data.repository.SpecializationRepository
import com.kednections.data.repository.TagRepository
import com.kednections.data.repository.UserRepository
import com.kednections.data.storage.TokenStorage
import com.kednections.data.storage.UserStorage
import com.kednections.domain.irepository.IGeoRepository
import com.kednections.domain.irepository.IPhotoRepository
import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.irepository.ISpecializationRepository
import com.kednections.domain.irepository.ITagRepository
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.istorage.ITokenStorage
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

    @Provides
    @Singleton
    fun provideTokenStorage(
        sharedPreferences: SharedPreferences,
    ): ITokenStorage {
        return TokenStorage(
            sharedPreferences = sharedPreferences,
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
    ): IUserRepository {
        return UserRepository(
            userApi = userApi,
        )
    }

    @Provides
    @Singleton
    fun provideGeoRepository(
        geoApi: GeoApi,
    ): IGeoRepository {
        return GeoRepository(
            geoApi = geoApi,
        )
    }

    @Provides
    @Singleton
    fun provideTagRepository(
        tagApi: TagApi,
    ): ITagRepository {
        return TagRepository(
            tagApi = tagApi,
        )
    }

    @Provides
    @Singleton
    fun provideSpecializationRepository(
        specializationApi: SpecializationsApi,
    ): ISpecializationRepository {
        return SpecializationRepository(
            specializationApi = specializationApi,
        )
    }

    @Provides
    @Singleton
    fun providePhotoRepository(
        fileApi: FileApi,
    ): IPhotoRepository {
        return PhotoRepository(
            fileApi = fileApi,
        )
    }

    @Provides
    @Singleton
    fun provideShowCaseRepository(
        showCaseApi: ShowCaseApi,
    ): IShowCaseRepository {
        return ShowCaseRepository(
            showCaseApi = showCaseApi,
        )
    }
}