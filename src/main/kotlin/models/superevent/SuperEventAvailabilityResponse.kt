package com.fathersprophets.backend.models.superevent

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventAvailabilityResponse(
    val superEventId: Int,
    val totalSeats: Int,
    val bookedCount: Int,
    val seatsLeft: Int,
    val waitingListLimit: Int,
    val waitingCount: Int,
    val waitingSeatsLeft: Int,
    val isFull: Boolean,
    val isBookingClosed: Boolean
)