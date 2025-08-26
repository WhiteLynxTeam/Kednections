package com.kednections.domain.usecase.user

import com.kednections.domain.irepository.IUserRepository
import com.kednections.domain.models.Tag
import com.kednections.domain.models.user.UserProfile
import com.kednections.domain.usecase.geo.GetCitiesApiUseCase
import com.kednections.domain.usecase.specialization.GetSpecializationApiUseCase
import com.kednections.domain.usecase.tag.GetTagsApiUseCase
import com.kednections.domain.usecase.token.GetTokenPrefUseCase

class GetUserApiUseCase(
    private val userRepository: IUserRepository,
    private val getTokenPrefUseCase: GetTokenPrefUseCase,
    // [yellow] когда подключим бд то внизу переменные убираем

    private val getCitiesApiUseCase: GetCitiesApiUseCase,
    private val getSpecializationApiUseCase: GetSpecializationApiUseCase,
    private val getTagsApiUseCase: GetTagsApiUseCase,
    private val getCommMethodApiUseCase: GetCommMethodApiUseCase,

    ) {
    suspend operator fun invoke(): UserProfile? {
        val token = getTokenPrefUseCase()
        val result = userRepository.getUser(token)

        println("GetUserApiUseCase result = $result")

        if (result.isSuccess) {
            val user = result.getOrNull()
            if (user != null) {

                //[red] очень галимый не красивый код для подстановки, наименований
                //все надо хранить в кэше, некогда делать
                //удалить потом и не позориться

                val cities = getCitiesApiUseCase()
                val cityMap = cities.associateBy({ it.id }, { it.name })
                println("cityMap = $cityMap")
                val communication = getCommMethodApiUseCase()
                val commMap = communication.associateBy({ it.id }, { it.name })
                val specialization = getSpecializationApiUseCase()
                val specMap = specialization.associateBy({ it.id }, { it.name })
                val tagsDef = getTagsApiUseCase(Tag.defaultTags)


                cityMap[user.city.id]?.let {
                    user.city = user.city.copy(name = it)
                }

                commMap[user.communicationMethod.id]?.let {
                    user.communicationMethod = user.communicationMethod.copy(name = it)
                }

                user.specializations = user.specializations.map { spec ->
                    specMap[spec.id]?.let {
                        spec.copy(name = it)
                    } ?: spec
                }

                user.tags = user.tags.map { tag ->
                    val valuesTag = tagsDef.find { it.id == tag.id }
                    valuesTag?.let {
                        tag.copy(
                            title = it.title,
                            description = it.description,
                            selectedIcon = it.selectedIcon,
                            unselectedIcon = it.unselectedIcon,
                        )
                    }!!
                }
                //[red] стереть потом нахер этот позор сверху

                println("GetUserApiUseCase user = $user")

                return user
            }
        }
        return null
    }
}