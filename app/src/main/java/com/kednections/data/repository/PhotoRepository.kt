package com.kednections.data.repository

import com.kednections.data.network.api.FileApi
import com.kednections.domain.irepository.IPhotoRepository
import com.kednections.domain.models.Token

class PhotoRepository(
    private val fileApi: FileApi,
) : IPhotoRepository {

//    override suspend fun getFile(token: Token, uuid: String): Result<String> {
    override suspend fun getFile(token: Token, uuid: String): Result<ByteArray?> {
        val response = fileApi.getFile(token.token, uuid)

//        val result: Result<String> = if (response.isSuccessful) {
        val result: Result<ByteArray?> = if (response.isSuccessful) {
            val bodyString = response.body()?.bytes()
            if (bodyString != null) {
                Result.success(bodyString)
            } else {
                Result.failure(Exception("Response body is null"))
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("Error response: ${response.code()} ${response.message()} - $errorBody"))
        }
        return result
    }
}