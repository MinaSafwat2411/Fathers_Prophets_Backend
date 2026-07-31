package com.fathersprophets.backend.models.notification

import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.database.dto.notification.NotificationDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNotificationRequest(
    val isRead: Boolean? = null,
    val eventId: Int? = null,
    val type: String? = null,
    val title: String? = null,
    val message: String? = null,
    val createdAt: String? = null
){
    fun convertToDto(id : Int) = NotificationDto(
        id = id,
        eventId = eventId ?: 0,
        type = try{
            EventType.valueOf(type ?: "")
        } catch (e: IllegalArgumentException) {
            EventType.bible
        },
        title = title ?: "",
        message = message ?: "",
        isRead = isRead ?: false,
        createdAt = createdAt ?: ""
    )
}