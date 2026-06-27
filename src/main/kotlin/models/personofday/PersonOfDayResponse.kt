package com.fathersprophets.backend.models.personofday

import kotlinx.serialization.Serializable

@Serializable
data class PersonOfDayResponse(
    val id: Int,
    val personId: Int,
    val message: String,
    val verse: String,
    val date: String
)