package com.fathersprophets.backend.models.eventmember

import kotlinx.serialization.Serializable

@Serializable
data class EventMemberResponse(
    val id: Int,
    val userId: Int,
    val eventId: Int,
    val name: String,
    val eventType: String
)
