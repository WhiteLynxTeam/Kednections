package com.kednections.domain.istorage

interface ITokenStorage {
    fun saveToken(token: String)
    fun getToken(): String

}
