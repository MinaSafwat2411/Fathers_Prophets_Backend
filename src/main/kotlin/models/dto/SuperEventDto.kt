package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.superevent.SuperEventResponse
import com.fathersprophets.backend.models.superevent.SuperEventTeacher

data class SuperEventDto(
    val id: Int,
    val title: String,
    val description: String?,
    val location: String?,
    val startDate: String,
    val endDate: String,
    val lastBookingDate: String,
    val totalSeats: Int,
    val waitingListLimit: Int,
    val image: String?,
    val createdAt: String,
    val teachers : List<SuperEventTeacher>
) {
    fun convertToResponse() = SuperEventResponse(
        id = id,
        title = title,
        description = description,
        location = location,
        startDate = startDate,
        endDate = endDate,
        lastBookingDate = lastBookingDate,
        totalSeats = totalSeats,
        waitingListLimit = waitingListLimit,
        image = image,
        createdAt = createdAt,
        teachers = teachers
    )
}