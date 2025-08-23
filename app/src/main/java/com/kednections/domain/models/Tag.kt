package com.kednections.domain.models

import com.kednections.R

data class Tag(
    val id: String? = null,
    val title: String,
    //[red] Когда сделают бэк нормальный, в котором все это будет, то убираем null
    val description: String? = null,
    val selectedIcon: Int? = null,
    val unselectedIcon: Int? = null,

    var isChecked: Boolean = false,
) {
    //[red] Так как это костыль, потому что с сервера должны передаваться иконки, а не по титлу должны
    //искать совпадения и присваивать id, то нам похер и на правильное заполнение текстовых полей
    //следовательно нехер сюда контекст тянуть
    //вместо того, чтобы бэк передавал нам всю информацию, мы херней страдаем
    //их время мы ценим, а наше время никого не интересует
    companion object {
        val defaultTags = listOf(
            Tag(
                title = "ищу друзей",
                description = "общаться и делиться идеями",
                selectedIcon = R.drawable.ic_friends_selected,
                unselectedIcon = R.drawable.ic_friends
            ),
            Tag(
                title = "ищу романтику",
                description = "открыт(а) к отношениям",
                selectedIcon = R.drawable.ic_romance_selected,
                unselectedIcon = R.drawable.ic_romance
            ),
            Tag(
                title = "ищу компанию",
                description = "для прогулок и походов на ивенты",
                selectedIcon = R.drawable.ic_company_selected,
                unselectedIcon = R.drawable.ic_company
            ),
            Tag(
                title = "ищу команду",
                description = "хочу присоединиться к проекту",
                selectedIcon = R.drawable.ic_looking_team_selected,
                unselectedIcon = R.drawable.ic_looking_team
            ),
            Tag(
                title = "собираю команду",
                description = "ищу людей в свой проект",
                selectedIcon = R.drawable.ic_assembling_team_selected,
                unselectedIcon = R.drawable.ic_assembling_team
            )
        )
    }
}