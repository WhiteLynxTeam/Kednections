package com.kednections.domain.irepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : IAuthRepository {
    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}