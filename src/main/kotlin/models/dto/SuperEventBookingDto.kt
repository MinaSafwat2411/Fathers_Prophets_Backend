package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.superevent.SuperEventBookingStatus
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse

data class SuperEventBookingDto(
    val id: Int,
    val superEventId: Int,
    val userId: Int,
    val name: String,
    val totalPaid: Int,
    val status: SuperEventBookingStatus,
    val createdAt: String,
    val teacherId: Int? = null
) {
    fun convertToResponse() = SuperEventBookingResponse(
        id = id,
        superEventId = superEventId,
        userId = userId,
        name = name,
        totalPaid = totalPaid,
        status = status.name,
        createdAt = createdAt,
        teacherId = teacherId
    )
}