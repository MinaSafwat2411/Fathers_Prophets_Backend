package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.PersonType
import com.fathersprophets.backend.models.person.PersonResponse

data class PersonDto(
    val id: Int,
    val name: String,
    val nickname: String?,
    val shortStory: String?,
    val fullStory: String?,
    val image: String?,
    val type: PersonType
) {
    fun convertToPersonResponse() = PersonResponse(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        shortStory = this.shortStory,
        fullStory = this.fullStory,
        image = this.image,
        type = this.type.name
    )
}
