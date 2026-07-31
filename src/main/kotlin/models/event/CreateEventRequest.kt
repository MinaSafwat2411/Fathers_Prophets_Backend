package com.fathersprophets.backend.models.event

import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.database.dto.event.EventDto
import com.fathersprophets.backend.database.dto.notification.NotificationDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateEventRequest(
    val title: String? = null,
    val dateTime: String? = null,
    val type: String? = null,
    val image: String? = null
){
    fun convertToEventDto() = EventDto(
        id = 0,
        title = title ?: "",
        dateTime = dateTime ?: "",
        image = image ?: "",
        type = try {
            EventType.valueOf(type ?: "bible")
        } catch (e: Exception) {
            EventType.bible
        }
    )

    fun convertToNotification(eventId: Int) = NotificationDto(
        id = 0,
        eventId = eventId,
        type = try {
            EventType.valueOf(type ?: "bible")
        } catch (e: Exception) {
            EventType.bible
        },
        title = title ?: "",
        message = "",
        isRead = false,
        createdAt = ""
    )
}
