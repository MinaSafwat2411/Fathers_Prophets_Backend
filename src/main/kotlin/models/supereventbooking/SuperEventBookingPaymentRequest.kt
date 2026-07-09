package com.fathersprophets.backend.models.supereventbooking

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventBookingPaymentRequest(
    val totalPaid: Int? = null,
    val teacherId: Int? = null,
    val bookingId: Int? = null
)
