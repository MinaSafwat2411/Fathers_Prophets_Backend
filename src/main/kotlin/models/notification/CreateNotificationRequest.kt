package com.fathersprophets.backend.models.notification

import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.models.dto.NotificationDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateNotificationRequest(
    val eventId: Int? = null,
    val type: String? = null,
    val title: String? = null,
    val message: String? = null
) {
    fun toNotificationDto() = NotificationDto(
        id = 0,
        eventId = eventId ?: 0,
        type = try {
            EventType.valueOf(type ?: "")
        } catch (e: Exception) {
            EventType.bible
        },
        title = title ?: "",
        message = message,
        isRead = false,
        createdAt = ""
    )
}