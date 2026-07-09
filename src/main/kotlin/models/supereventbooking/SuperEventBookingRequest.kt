package com.fathersprophets.backend.models.supereventbooking

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventBookingRequest(
    val superEventId: Int? = null
)