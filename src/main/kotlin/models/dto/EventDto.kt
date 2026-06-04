package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.EventType
import com.fathersprophets.backend.models.event.EventResponse

data class EventDto(
    val id: Int,
    val type: EventType,
    val title: String,
    val dateTime: String,
    val image: String
){
    fun convertToEventResponse() = EventResponse(
        id = id,
        type = type.name,
        dateTime = dateTime,
        image = image,
        title = title
    )
}