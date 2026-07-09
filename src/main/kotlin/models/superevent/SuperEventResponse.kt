package com.fathersprophets.backend.models.superevent

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventResponse(
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
    val teachers : List<SuperEventTeacher>?
)