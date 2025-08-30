package com.kednections.domain.usecase.showcase

import com.kednections.domain.models.showcase.PhotoShowCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class UploadListPhotoShowCaseApiUseCase(
    private val uploadPhotoShowCaseApiUseCase: UploadPhotoShowCaseApiUseCase,
) {
    suspend operator fun invoke(listItem: List<PhotoShowCase>) {
        /*              [yellow]  Рекомендации
                При большом числе элементов можно ограничить количество параллельных загрузок с помощью Semaphore.

                Можно добавить обработку ошибок для отдельных загрузок, чтобы одна не сломала всю цепочку.*/
        return coroutineScope {
            listItem.map {
                async {
                    uploadPhotoShowCaseApiUseCase(it)
                }
            }.awaitAll()
        }
    }
}