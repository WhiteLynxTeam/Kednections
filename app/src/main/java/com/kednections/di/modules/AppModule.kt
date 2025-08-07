package com.kednections.di.modules

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kednections.data.network.api.UserApi
import com.kednections.data.repository.UserRepository
import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.istorage.IUserStorage
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import com.kednections.domain.usecase.specialization.GetSpecializationApiUseCase
import com.kednections.domain.usecase.tag.GetTagsApiUseCase
import com.kednections.domain.usecase.user.GetCurrentUserUseCase
import com.kednections.domain.usecase.user.GetIsFirstRunUseCase
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
import com.kednections.domain.usecase.user.SetFirstRunCompletedUseCase
import com.kednections.view.auth.AuthViewModel
import com.kednections.view.form.geolocation.GeolocationViewModel
import com.kednections.view.form.nickname.NickNameViewModel
import com.kednections.view.form.specialization.SpecializationViewModel
import com.kednections.view.form.welcome.WelcomeViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {

    @Provides
    fun provideActivityAuthViewModelFactory(
        authFirebase: FirebaseAuth,
        credentialManager: CredentialManager,
        request: GetCredentialRequest,
        loginUserApiUseCase: LoginUserApiUseCase,
        registerUserApiUseCase: RegisterUserApiUseCase,
    ) = AuthViewModel.Factory(
        authFirebase = authFirebase,
        credentialManager = credentialManager,
        request = request,
        loginUserApiUseCase = loginUserApiUseCase,
        registerUserApiUseCase = registerUserApiUseCase,
    )


    @Provides
    @Singleton
    fun provideWelcomeViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        getIsFirstRunUseCase: GetIsFirstRunUseCase,
        setFirstRunCompletedUseCase: SetFirstRunCompletedUseCase
    ): WelcomeViewModel.Factory {
        return WelcomeViewModel.Factory(
            getCurrentUserUseCase,
            getIsFirstRunUseCase,
            setFirstRunCompletedUseCase
        )
    }

    @Provides
    fun provideNickNameViewModelFactory(

    ) = NickNameViewModel.Factory(

    )

    @Provides
    fun provideSpecializationViewModelFactory(
        getSpecializationApiUseCase: GetSpecializationApiUseCase,
    ) = SpecializationViewModel.Factory(
        getSpecializationApiUseCase = getSpecializationApiUseCase,
    )

    @Provides
    fun provideGeolocationViewModelFactory(
        getCitiesApiUseCase: GetCitiesApiUseCase,
    ) = GeolocationViewModel.Factory(
        getCitiesApiUseCase = getCitiesApiUseCase,
    )
}
