package com.fathersprophets.backend.models.timeline

import kotlinx.serialization.Serializable

@Serializable
data class TimelineResponse(
    val id: Int,
    val event1: String,
    val event2: String,
    val event3: String,
    val event4: String
)