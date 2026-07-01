package com.fathersprophets.backend.models.timelineanswer

import kotlinx.serialization.Serializable

@Serializable
data class CreateTimelineAnswerRequest(
    val timelineId: Int,
    val userId: Int,
    val order: List<Int>
)