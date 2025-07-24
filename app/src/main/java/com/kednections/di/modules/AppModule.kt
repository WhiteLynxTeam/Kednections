package com.kednections.di.modules

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.firebase.auth.FirebaseAuth
import com.kednections.view.auth.AuthViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule() {

    @Provides
    fun provideActivityAuthViewModelFactory(
        authFirebase: FirebaseAuth,
        credentialManager: CredentialManager,
        request: GetCredentialRequest,
    ) = AuthViewModel.Factory(
        authFirebase = authFirebase,
        credentialManager = credentialManager,
        request = request,
    )
}
