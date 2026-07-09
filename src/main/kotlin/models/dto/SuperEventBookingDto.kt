package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.SuperEventBookingStatus
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse

data class SuperEventBookingDto(
    val id: Int,
    val superEventId: Int,
    val userId: Int,
    val userName: String,
    val status: SuperEventBookingStatus,
    val createdAt: String
) {
    fun convertToResponse() = SuperEventBookingResponse(
        id = id,
        superEventId = superEventId,
        userId = userId,
        userName = userName,
        status = status.name,
        createdAt = createdAt
    )
}