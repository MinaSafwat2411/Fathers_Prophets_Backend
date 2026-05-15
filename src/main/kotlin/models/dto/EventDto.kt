package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.event.EventResponse

data class EventDto(
    val id: Int,
    val name: String,
    val dateTime: String,
    val image: String
){
    fun convertToEventResponse() = EventResponse(
        id = id,
        name = name,
        dateTime = dateTime,
        image = image
    )
}