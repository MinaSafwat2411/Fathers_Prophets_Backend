package com.fathersprophets.backend.models.person

import com.fathersprophets.backend.database.tables.PersonType
import com.fathersprophets.backend.models.dto.PersonDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonRequest(
    val name: String? = null,
    val nickname: String? = null,
    val shortStory: String? = null,
    val fullStory: String? = null,
    val image: String? = null,
    val type: String? = null,
){
    fun toPersonDto(id: Int) = PersonDto(
        id = id,
        name = this.name ?: "",
        nickname = this.nickname ?: "",
        shortStory = this.shortStory ?: "",
        fullStory = this.fullStory ?: "",
        image = this.image ?: "",
        type = try {
            PersonType.valueOf(this.type ?: "")
        }catch (e: Exception){
            PersonType.prophets
        }
    )
}
