package com.kednections.view.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kednections.domain.models.user.User
import com.kednections.domain.usecase.user.CheckUserApiUseCase
import com.kednections.domain.usecase.user.LoginUserApiUseCase
import com.kednections.utils.AuthValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val authFirebase: FirebaseAuth,
    private val credentialManager: CredentialManager,
    private val request: GetCredentialRequest,
    private val loginUserApiUseCase: LoginUserApiUseCase,
    private val checkUserApiUseCase: CheckUserApiUseCase,
) : ViewModel() {
    private var _login = MutableStateFlow("")
    val login: StateFlow<String>
        get() = _login.asStateFlow()
    private var _pass = MutableStateFlow("")
    val pass: StateFlow<String>
        get() = _pass.asStateFlow()
    private var _switcher = MutableStateFlow<Int?>(null)
    val switcher: StateFlow<Int?>
        get() = _switcher.asStateFlow()

//    private var _currentUser = MutableStateFlow<FirebaseUser?>(null)
//    val currentUser: SharedFlow<FirebaseUser?>
//        get() = _currentUser.asStateFlow()

    private var _isRegistry = MutableSharedFlow<Boolean>()
    val isRegistry: SharedFlow<Boolean>
        get() = _isRegistry.asSharedFlow()

    private var _isLogin = MutableSharedFlow<Boolean>()
    val isLogin: SharedFlow<Boolean>
        get() = _isLogin.asSharedFlow()

    private var _isIt = MutableSharedFlow<Pair<Boolean,Boolean>>()
    val isIt: SharedFlow<Pair<Boolean,Boolean>>
        get() = _isIt.asSharedFlow()

    fun setLoginPass(login: String, pass: String, authValidator: AuthValidator) {
        _login.value = login
        _pass.value = pass
        _switcher.value = authValidator.getGelectedInt()
        if (_switcher.value == 0) {
            login()
        } else {
            verify(_login.value)
        }
    }

    private fun login() {
        viewModelScope.launch {
            _isLogin.emit(loginUserApiUseCase(User(_login.value, _pass.value)))
        }
    }

    fun login(user: User) {
        viewModelScope.launch {
            _isLogin.emit(loginUserApiUseCase(user))
        }
    }

    fun verify(email: String) {
        viewModelScope.launch {
            _isIt.emit(checkUserApiUseCase(email))
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            _isRegistry.emit(true)
        }
    }

    fun signInWithGoogle(
        context: Context,
        onSuccess: (user: FirebaseUser?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request,
                )
                val credential = result.credential
                // Use googleIdTokenCredential and extract the ID to validate and
                // authenticate on your server.
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                // You can use the members of googleIdTokenCredential directly for UX
                // purposes, but don't use them to store or control access to user
                // data. For that you first need to validate the token:
                val googleIdToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

                println("signInWithGoogle:authFirebase - ${authFirebase.currentUser?.isEmailVerified}")
                println("signInWithGoogle:authFirebase - ${authFirebase.currentUser?.email}")
                println("signInWithGoogle:authFirebase - ${authFirebase.currentUser?.displayName}")
                println("signInWithGoogle:authFirebase - ${authFirebase.currentUser?.phoneNumber}")
                println("signInWithGoogle:authFirebase - ${authFirebase.currentUser?.photoUrl}")
                println("signInWithGoogle:authFirebase - ${authFirebase.currentUser?.metadata}")

                val authResult = authFirebase.signInWithCredential(firebaseCredential).await()
                if (authResult != null) {
                    println("signInWithGoogle:success")
                    println("signInWithGoogle:authFirebase - ${authResult.user?.email}")

                    onSuccess(authResult.user)

//                    authFirebase.currentUser?.email?.let { saveEmailPrefUseCase(it) }
//
//                    _isAuth.emit(Pair(true,""))
                } else {
                    println("signInWithGoogle:failure")
                    onFailure(Exception("User is null"))
                }

            } catch (e: GetCredentialException) {
                println("catch signInWithGoogle:failure -> onFailure: ${e.errorMessage}")
                val ex = e.errorMessage
                if (ex?.contains("No credentials available", false) == true) {
                    onFailure(Exception("No credentials available"))
                }
                onFailure(e)
//                _isAuth.emit(Pair(false,"Credentials are missing. Add google account."))
            }
        }
    }

    class Factory(
        private val authFirebase: FirebaseAuth,
        private val credentialManager: CredentialManager,
        private val request: GetCredentialRequest,
        private val loginUserApiUseCase: LoginUserApiUseCase,
        private val checkUserApiUseCase: CheckUserApiUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(
                    authFirebase = authFirebase,
                    credentialManager = credentialManager,
                    request = request,
                    loginUserApiUseCase = loginUserApiUseCase,
                    checkUserApiUseCase = checkUserApiUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}