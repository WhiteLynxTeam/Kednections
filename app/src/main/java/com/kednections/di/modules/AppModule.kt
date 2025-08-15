package com.kednections.di.modules

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.firebase.auth.FirebaseAuth
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import com.kednections.domain.usecase.specialization.GetSpecializationApiUseCase
import com.kednections.domain.usecase.tag.GetTagsApiUseCase
import com.kednections.domain.usecase.user.GetCurrentUserUseCase
import com.kednections.domain.usecase.user.GetIsFirstRunUseCase
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
import com.kednections.domain.usecase.user.SaveUserDataPrefUseCase
import com.kednections.domain.usecase.user.SetFirstRunCompletedUseCase
import com.kednections.view.activity.FormActivityViewModel
import com.kednections.view.auth.AuthViewModel
import com.kednections.view.form.about.AboutViewModel
import com.kednections.view.form.choose_communicate.ChooseCommunicateViewModel
import com.kednections.view.form.geolocation.GeolocationViewModel
import com.kednections.view.form.nickname.NickNameViewModel
import com.kednections.view.form.purposes.PurposesViewModel
import com.kednections.view.form.specialization.SpecializationViewModel
import com.kednections.view.form.welcome.WelcomeViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {

    @Provides
    fun provideFormActivityViewModelFactory(
        registerUserApiUseCase: RegisterUserApiUseCase,
    ) = FormActivityViewModel.Factory(
        registerUserApiUseCase = registerUserApiUseCase,
    )

    @Provides
    fun provideAuthViewModelFactory(
        authFirebase: FirebaseAuth,
        credentialManager: CredentialManager,
        request: GetCredentialRequest,
        loginUserApiUseCase: LoginUserApiUseCase,
    ) = AuthViewModel.Factory(
        authFirebase = authFirebase,
        credentialManager = credentialManager,
        request = request,
        loginUserApiUseCase = loginUserApiUseCase,
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
        saveUserDataPrefUseCase: SaveUserDataPrefUseCase
    ): NickNameViewModel.Factory {
        return NickNameViewModel.Factory(saveUserDataPrefUseCase)
    }

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

    @Provides
    fun providePurposesViewModelFactory(
        getTagsApiUseCase: GetTagsApiUseCase,
    ) = PurposesViewModel.Factory(
        getTagsApiUseCase = getTagsApiUseCase,
    )

    @Provides
    fun provideChooseCommunicateViewModelFactory(
    ) = ChooseCommunicateViewModel.Factory(
    )

    @Provides
    fun provideAboutViewModelFactory(
    ) = AboutViewModel.Factory(
    )
}
