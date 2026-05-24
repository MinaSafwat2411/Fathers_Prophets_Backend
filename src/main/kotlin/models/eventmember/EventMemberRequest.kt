package com.fathersprophets.backend.models.eventmember

import com.fathersprophets.backend.models.dto.EventMemberDto
import kotlinx.serialization.Serializable

@Serializable
data class EventMemberRequest(
    val userId: Int,
    val eventId: Int,
    val name: String,
    val eventType: String
){
    fun toEventMemberDto(id: Int) = EventMemberDto(
        id = id,
        userId = userId,
        eventId = eventId,
        name = name,
        eventType = eventType
    )
}
