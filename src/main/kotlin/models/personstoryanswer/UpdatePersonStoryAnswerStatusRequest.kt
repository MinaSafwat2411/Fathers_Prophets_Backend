package com.fathersprophets.backend.models.personstoryanswer

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonStoryAnswerStatusRequest(
    val status: String
)