package com.fathersprophets.backend.models.event

import com.fathersprophets.backend.models.dto.EventDto
import kotlinx.serialization.Serializable

@Serializable
data class EventRequest(
    val name: String? = null,
    val dateTime: String? = null,
    val image: String? = null
){
    fun convertToEventDto(id: Int = 0) = EventDto(
        id = id,
        name = name ?: "",
        dateTime = dateTime ?: "",
        image = image ?: ""
    )
}
