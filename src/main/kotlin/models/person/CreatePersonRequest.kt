package com.fathersprophets.backend.models.person

import com.fathersprophets.backend.database.tables.PersonType
import com.fathersprophets.backend.models.dto.PersonDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonRequest(
    val name: String?,
    val nickname: String?,
    val shortStory: String?,
    val fullStory: String?,
    val image: String?,
    val type: String?
){
    fun toPersonDto() = PersonDto(
        id = 0,
        name = name?:"",
        nickname = nickname?:"",
        shortStory = shortStory?:"",
        fullStory = fullStory?:"",
        image = image?:"",
        type = try{
            PersonType.valueOf(type?:"")
        }catch (e: Exception){
            PersonType.prophets
        }
    )
}
