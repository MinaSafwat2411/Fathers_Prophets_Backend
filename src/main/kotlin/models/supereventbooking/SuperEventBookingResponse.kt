package com.fathersprophets.backend.models.supereventbooking

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventBookingResponse(
    val id: Int,
    val superEventId: Int,
    val userId: Int,
    val userName: String,
    val status: String,
    val createdAt: String
)