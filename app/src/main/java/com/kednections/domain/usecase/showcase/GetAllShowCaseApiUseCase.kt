package com.kednections.domain.usecase.showcase

import com.kednections.domain.irepository.IShowCaseRepository
import com.kednections.domain.models.showcase.PhotoShowCase
import com.kednections.domain.usecase.photo.GetPhotoApiUseCase
import com.kednections.domain.usecase.token.GetTokenPrefUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetAllShowCaseApiUseCase(
    private val showCaseRepository: IShowCaseRepository,
    private val getTokenPrefUseCase: GetTokenPrefUseCase,
    private val getPhotoApiUseCase: GetPhotoApiUseCase,
    ) {
    suspend operator fun invoke(showcaseId: String): List<PhotoShowCase> {
        val token = getTokenPrefUseCase()
        val result = showCaseRepository.getAll(token, showcaseId)

        if (result.isSuccess) {
            val photos = result.getOrNull()
            if (photos != null) {
/*              [yellow]  Рекомендации
                При большом числе элементов можно ограничить количество параллельных загрузок с помощью Semaphore.

                Можно добавить обработку ошибок для отдельных загрузок, чтобы одна не сломала всю цепочку.*/
                return coroutineScope {
                    photos.map { photoShowCase ->
                        async {
                            val photoData = getPhotoApiUseCase(photoShowCase.id)
                            // Вернуть новый объект с загруженной фоткой
                            photoData?.let { photoShowCase.copy(photo = photoData) }
                                ?: photoShowCase
                        }
                    }.awaitAll()
                }
            }
        }
        return emptyList()
    }
}