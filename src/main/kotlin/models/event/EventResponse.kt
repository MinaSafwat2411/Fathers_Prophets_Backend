package com.fathersprophets.backend.models.event

import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    val id: Int,
    val type: String,
    val dateTime: String,
    val title: String,
    val image: String
)
