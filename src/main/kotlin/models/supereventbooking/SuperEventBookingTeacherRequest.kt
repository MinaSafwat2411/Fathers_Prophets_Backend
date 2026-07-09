package com.fathersprophets.backend.models.supereventbooking

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventBookingTeacherRequest(
    val teacherId: Int? = null
)
