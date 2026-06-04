package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.EventType
import com.fathersprophets.backend.models.eventmember.EventMemberResponse

data class EventMemberDto(
    val id: Int,
    val eventId: Int,
    val userId: Int,
    val name: String,
    val eventType: EventType
){
    fun toEventMemberResponse() = EventMemberResponse(
        id = this.id,
        eventId = this.eventId,
        userId = this.userId,
        name = this.name,
        eventType = this.eventType.name
    )
}
