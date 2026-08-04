package com.fathersprophets.backend.modules.eventmember

import kotlinx.serialization.Serializable

@Serializable
data class EventMemberDto(
    val id: Int,
    val eventId: Int,
    val userId: Int
)

@Serializable
data class EventMemberCreateDto(
    val eventId: Int,
    val userId: Int
)

@Serializable
data class EventMemberUpdateDto(
    val eventId: Int? = null,
    val userId: Int? = null
)