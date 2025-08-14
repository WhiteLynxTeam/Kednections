package com.kednections.data.storage

import android.content.SharedPreferences
import com.kednections.domain.istorage.ITokenStorage
import androidx.core.content.edit

class TokenStorage(
    private val sharedPreferences: SharedPreferences

) : ITokenStorage {
    override fun saveToken(token: String) {
        sharedPreferences.edit { putString(TOKEN, token) }
    }

    override fun getToken(): String {
        val token = sharedPreferences.getString(TOKEN, "") ?: ""
        return token
    }

    companion object {
        private const val TOKEN = "token"
    }
}
