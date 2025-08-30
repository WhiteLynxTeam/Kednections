package com.kednections.data.repository

import com.kednections.data.network.api.ShowCaseApi
import com.kednections.data.network.dto.showcase.response.ItemShowCaseResponse
import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.models.Token
import com.kednections.domain.models.showcase.PhotoShowCase
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ShowCaseRepository(
    private val showCaseApi: ShowCaseApi,
) : IShowCaseRepository {

    override suspend fun upload(token: Token, item: PhotoShowCase): Result<String> {

        val mimeType = "image/*"
        val fileName = "photo.jpg"
        val requestBody = item.photo?.toRequestBody(mimeType.toMediaTypeOrNull())
        val partFile = requestBody?.let {
            MultipartBody.Part.createFormData(
                name = "file",
                filename = null,
                body = it
            )
        }

        val response = showCaseApi.upload(token.token, item.toMultipartParts(), partFile)

        val result: Result<String> = if (response.isSuccessful) {
            val bodyString = response.body()?.string()
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

    override suspend fun getAll(token: Token, showcaseId: String): Result<List<PhotoShowCase>> {
        val result = showCaseApi.getAll(token.token, showcaseId)
        return result.map { it.toListPhotoShowCase() }
    }

    private fun PhotoShowCase.toMultipartParts(): Map<String, @JvmSuppressWildcards RequestBody> {
        val map = mutableMapOf<String, RequestBody>()

        //[red]бэк немного корявый, поэтому и мы жестко закинули uuid для всех полей?
        //чтобы проскачить регистрацию и перейти к главному функционалу

        //[yellow] бэк не проверяет совпадение по тэгам и специализации
        //забыл поменять при копировании uuid с communication_method
        //в тэгах и специализации - регистрация прошла
        val title = this.title ?: ""
        map["title"] = title.toRequestBody("text/plain".toMediaType())
        map["description"] = this.description.toRequestBody("text/plain".toMediaType())
//        map["file"] = this.photo.toRequestBody("image/*".toMediaType())
        return map
    }

    private fun ItemShowCaseResponse.toPhotoShowCase(): PhotoShowCase {
        return PhotoShowCase(
            id = this.filePath,
            title = this.title,
            description = this.description,
            photo = null
//            photo = ""
        )
    }

    private fun List<ItemShowCaseResponse>.toListPhotoShowCase(): List<PhotoShowCase> {
        return this.map { it.toPhotoShowCase() }
    }
}