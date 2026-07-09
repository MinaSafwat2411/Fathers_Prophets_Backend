package com.fathersprophets.backend.models.supereventbooking

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventBookingResponse(
    val id: Int,
    val superEventId: Int,
    val userId: Int,
    val name: String,
    val totalPaid: Int,
    val status: String,
    val createdAt: String,
    val teacherId: Int?
)