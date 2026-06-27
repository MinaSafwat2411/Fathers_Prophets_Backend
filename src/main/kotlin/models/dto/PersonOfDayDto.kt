package com.fathersprophets.backend.models.dto

import java.time.LocalDate

data class PersonOfDayDto(
    val id: Int,
    val personId: Int,
    val message: String,
    val verse: String,
    val date: LocalDate
)