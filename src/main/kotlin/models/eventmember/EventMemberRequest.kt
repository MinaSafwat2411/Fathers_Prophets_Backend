package com.fathersprophets.backend.models.eventmember

import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.database.dto.EventMemberDto
import kotlinx.serialization.Serializable

@Serializable
data class EventMemberRequest(
    val userId: Int? = null,
    val eventId: Int? = null,
    val name: String? = null,
    val eventType: String? = null
){
    fun toEventMemberDto(id: Int) = EventMemberDto(
        id = id,
        userId = userId?:0,
        eventId = eventId?:0,
        name = name?:"",
        eventType = try {
            EventType.valueOf(eventType?:"")
        } catch (e: Exception) {
            EventType.bible
        }
    )
}
