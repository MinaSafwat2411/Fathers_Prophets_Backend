package com.fathersprophets.backend.models.supereventbooking

import com.fathersprophets.backend.database.tables.SuperEventBookingStatus
import com.fathersprophets.backend.models.dto.SuperEventBookingDto
import kotlinx.serialization.Serializable

@Serializable
data class SuperEventBookingPaymentRequest(
    val totalPaid: Int? = null,
    val teacherId: Int? = null,
    val bookingId: Int? = null
){
    fun superEventIdToDto() = SuperEventBookingDto(
        id = bookingId ?: 0,
        superEventId = 0,
        userId = 0,
        name = "",
        totalPaid = totalPaid ?: 0,
        status = SuperEventBookingStatus.booked,
        createdAt = ""
    )
}
