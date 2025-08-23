package com.kednections.di.modules

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Singleton

@Module
class AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideCredentialManager(context: Context) = CredentialManager.create(context)

    @Provides
    @Singleton
    fun provideGoogleIdOption(): GetGoogleIdOption {

        val ranNonce : String = UUID.randomUUID().toString()
        val bytes : ByteArray = ranNonce.toByteArray()
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        val digest: ByteArray = md.digest(bytes)
        val hashedNonce : String = digest.fold(""){str, it -> str + "%02x".format(it)}

        return GetGoogleIdOption.Builder()
            // enable this option if you like to choose btw accounts
            .setFilterByAuthorizedAccounts(false)
            // here set our authentication server client id
            .setServerClientId(WEB_CLIENT_ID)
            // optional for security purposes
            .setNonce(hashedNonce)
            // just enable it ( no idea what it does )
            .setAutoSelectEnabled(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideCredentialRequest(googleIdOption: GetGoogleIdOption): GetCredentialRequest =
        GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

    companion object {
        const val WEB_CLIENT_ID =
            "656594934317-flug8osqqchllts4orv4sds0i2r9pp9c.apps.googleusercontent.com"
        //Сверху почта зарегистрированная на приложение - общая
        //Под моим личным аккаунтом.
//            "1085539908512-rl3upol0ar4ci67d09lhsnv81m1p8feh.apps.googleusercontent.com"
    }
}