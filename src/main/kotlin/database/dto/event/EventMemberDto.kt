package com.fathersprophets.backend.database.dto.event

data class EventMemberDto(
    val id: Int,
    val eventId: Int,
    val userId: Int,
)

