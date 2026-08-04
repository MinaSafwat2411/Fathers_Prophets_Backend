package com.fathersprophets.backend.modules.notification


import com.fathersprophets.backend.database.enums.NotificationType
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val id: Int,
    val type: NotificationType,
    val title: String,
    val description: String,
    val referenceId: Int?,
    val createdAt: String
)

@Serializable
data class NotificationCreateDto(
    val type: NotificationType,
    val title: String,
    val description: String,
    val referenceId: Int? = null
)

@Serializable
data class NotificationUpdateDto(
    val type: NotificationType? = null,
    val title: String? = null,
    val description: String? = null,
    val referenceId: Int? = null
)