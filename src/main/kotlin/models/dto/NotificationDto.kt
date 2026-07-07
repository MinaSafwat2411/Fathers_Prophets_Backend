package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.EventType
import com.fathersprophets.backend.models.notification.NotificationResponse

data class NotificationDto(
    val id: Int,
    val eventId: Int,
    val type: EventType,
    val title: String,
    val message: String?,
    val isRead: Boolean,
    val createdAt: String
) {
    fun convertToResponse() = NotificationResponse(
        id = id,
        eventId = eventId,
        type = type.name,
        title = title,
        message = message,
        isRead = isRead,
        createdAt = createdAt
    )
}