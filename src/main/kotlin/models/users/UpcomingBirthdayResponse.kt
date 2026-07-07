package com.fathersprophets.backend.models.users

import kotlinx.serialization.Serializable

@Serializable
data class UpcomingBirthdayResponse(
    val id: Int,
    val name: String,
    val username: String,
    val profile: String? = null,
    val birthDate: String,
    val nextBirthdayDate: String,
    val daysUntil: Long
)