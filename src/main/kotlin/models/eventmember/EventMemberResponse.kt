package com.fathersprophets.backend.models.eventmember

data class EventMemberResponse(
    val id: Int,
    val userId: Int,
    val eventId: Int,
    val name: String
)
