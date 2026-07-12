package com.fathersprophets.backend.models.event

import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.models.dto.EventDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateEventRequest(
    val title: String? = null,
    val dateTime: String? = null,
    val type: String? = null,
    val image: String? = null
){
    fun convertToEventDto(id: Int) = EventDto(
        id = id,
        title = title ?: "",
        dateTime = dateTime ?: "",
        image = image ?: "",
        type = try {
            EventType.valueOf(type ?: "bible")
        } catch (e: Exception) {
            EventType.bible
        }
    )
}
