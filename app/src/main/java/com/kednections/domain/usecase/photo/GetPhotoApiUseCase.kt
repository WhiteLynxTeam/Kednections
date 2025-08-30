package com.kednections.domain.usecase.photo

import com.kednections.domain.irepository.IPhotoRepository
import com.kednections.domain.usecase.token.GetTokenPrefUseCase

class GetPhotoApiUseCase(
    private val photoRepository: IPhotoRepository,
    private val getTokenPrefUseCase: GetTokenPrefUseCase,
) {
//    suspend operator fun invoke(uuid: String): String? {
    suspend operator fun invoke(uuid: String): ByteArray? {
        val token = getTokenPrefUseCase()
        val result = photoRepository.getFile(token, uuid)
        println("GetPhotoApiUseCase result = $result")
        if (result.isSuccess) {
            val photo = result.getOrNull()
            if (photo != null) {
                return photo
            }
        }
        return null
    }
}