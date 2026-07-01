package com.fathersprophets.backend.models.timelineanswer

import kotlinx.serialization.Serializable

@Serializable
data class TimelineAnswerResponse(
    val id: Int,
    val timelineId: Int,
    val userId: Int,
    val status: String
)