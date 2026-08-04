package com.fathersprophets.backend.modules.superevent

import kotlinx.serialization.Serializable

@Serializable
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
    val teachers: String
)

@Serializable
data class SuperEventCreateDto(
    val title: String,
    val description: String? = null,
    val location: String? = null,
    val startDate: String,
    val endDate: String,
    val lastBookingDate: String,
    val totalSeats: Int,
    val waitingListLimit: Int = 0,
    val image: String? = null,
    val teachers: String = "[]"
)

@Serializable
data class SuperEventUpdateDto(
    val title: String? = null,
    val description: String? = null,
    val location: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val lastBookingDate: String? = null,
    val totalSeats: Int? = null,
    val waitingListLimit: Int? = null,
    val image: String? = null,
    val teachers: String? = null
)