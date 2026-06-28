package com.fathersprophets.backend.models.personofday

import com.fathersprophets.backend.models.dto.PersonOfDayDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonOfDayRequest(
    val personId: Int? = null,
    val message: String? = null,
    val verse: String? = null,
    val date: String? = null
){
    fun convertToPersonOfDayDto(id: Int) = PersonOfDayDto(
        id = id,
        personId = this.personId ?: 0,
        message = this.message ?: "",
        verse = this.verse ?: "",
        date = java.time.LocalDate.parse(this.date?:"")
    )
}
