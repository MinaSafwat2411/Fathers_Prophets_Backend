package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.personofday.PersonOfDayResponse
import java.time.LocalDate

data class PersonOfDayDto(
    val id: Int,
    val personId: Int,
    val message: String,
    val verse: String,
    val date: LocalDate
) {
    fun convertToResponse() = PersonOfDayResponse(
        id = id,
        personId = personId,
        message = message,
        verse = verse,
        date = date.toString()
    )
}