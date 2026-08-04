package com.fathersprophets.backend.modules.supereventbooking

import com.fathersprophets.backend.database.enums.SuperEventBookingStatus
import kotlinx.serialization.Serializable

@Serializable
data class SuperEventBookingDto(
    val id: Int,
    val superEventId: Int,
    val userId: Int,
    val name: String,
    val totalPaid: Int,
    val status: SuperEventBookingStatus,
    val createdAt: String,
    val teacherId: Int?
)

@Serializable
data class SuperEventBookingCreateDto(
    val superEventId: Int,
    val userId: Int,
    val name: String,
    val totalPaid: Int = 0,
    val status: SuperEventBookingStatus,
    val teacherId: Int? = null
)

@Serializable
data class SuperEventBookingUpdateDto(
    val superEventId: Int? = null,
    val userId: Int? = null,
    val name: String? = null,
    val totalPaid: Int? = null,
    val status: SuperEventBookingStatus? = null,
    val teacherId: Int? = null
)