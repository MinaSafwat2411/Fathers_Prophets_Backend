package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.event.EventResponse

data class EventDto(
    val id: Int,
    val type: String,
    val title: String,
    val dateTime: String,
    val image: String
){
    fun convertToEventResponse() = EventResponse(
        id = id,
        type = type,
        dateTime = dateTime,
        image = image,
        title = title
    )
}