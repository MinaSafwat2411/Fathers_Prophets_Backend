package com.fathersprophets.backend.modules.event


import com.fathersprophets.backend.database.enums.EventType
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: Int,
    val type: EventType,
    val title: String,
    val dateTime: String,
    val image: String?,
    val familyId: Int
)

@Serializable
data class EventCreateDto(
    val type: EventType,
    val title: String,
    val dateTime: String,
    val image: String? = null,
    val familyId: Int
)

@Serializable
data class EventUpdateDto(
    val type: EventType? = null,
    val title: String? = null,
    val dateTime: String? = null,
    val image: String? = null,
    val familyId: Int? = null
)