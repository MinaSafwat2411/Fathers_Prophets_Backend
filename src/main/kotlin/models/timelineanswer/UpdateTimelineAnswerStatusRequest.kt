package com.fathersprophets.backend.models.timelineanswer

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTimelineAnswerStatusRequest(
    val status: String
)