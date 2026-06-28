package com.fathersprophets.backend.models.personofday

import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonOfDayRequest(
    val personId: Int? = null,
    val message: String? = null,
    val verse: String? = null,
    val date: String? = null
)