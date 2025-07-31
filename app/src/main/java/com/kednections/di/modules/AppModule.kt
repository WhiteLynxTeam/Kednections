package com.kednections.di.modules

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.firebase.auth.FirebaseAuth
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.domain.usecase.user.RegisterUserApiUseCase
import com.kednections.view.auth.AuthViewModel
import com.kednections.view.welcome.WelcomeViewModel
import dagger.Module
import dagger.Provides

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
    fun provideWelcomeViewModelFactory(

    ) = WelcomeViewModel.Factory(

    )
}
