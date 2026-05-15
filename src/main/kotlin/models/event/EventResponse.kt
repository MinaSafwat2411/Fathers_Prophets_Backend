package com.fathersprophets.backend.models.event

import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    val id: Int,
    val name: String,
    val dateTime: String,
    val image: String
)
