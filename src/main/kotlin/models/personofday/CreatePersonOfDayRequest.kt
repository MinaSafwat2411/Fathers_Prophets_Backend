package com.fathersprophets.backend.models.personofday

import com.fathersprophets.backend.models.dto.PersonOfDayDto
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CreatePersonOfDayRequest(
    val personId: Int? = null,
    val message: String? = null,
    val verse: String? = null,
    val date: String? = null
){
    fun convertToDto() = PersonOfDayDto(
        id = 0,
        personId = personId?:0,
        message = message?:"",
        verse = verse?:"",
        date = LocalDate.parse(date?:"")
    )
}