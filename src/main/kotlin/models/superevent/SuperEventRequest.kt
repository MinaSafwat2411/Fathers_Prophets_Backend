package com.fathersprophets.backend.models.superevent

import com.fathersprophets.backend.models.dto.SuperEventDto
import kotlinx.serialization.Serializable

@Serializable
data class SuperEventRequest(
    val title: String? = null,
    val description: String? = null,
    val location: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val lastBookingDate: String? = null,
    val totalSeats: Int? = null,
    val waitingListLimit: Int? = null,
    val image: String? = null
) {
    fun convertToDto(id: Int) = SuperEventDto(
        id = id,
        title = title ?: "",
        description = description,
        location = location,
        startDate = startDate ?: "",
        endDate = endDate ?: "",
        lastBookingDate = lastBookingDate ?: "",
        totalSeats = totalSeats ?: 0,
        waitingListLimit = waitingListLimit ?: 0,
        image = image,
        createdAt = ""
    )
}