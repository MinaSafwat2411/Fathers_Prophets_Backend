package com.fathersprophets.backend.models.guesspersonanswer

import kotlinx.serialization.Serializable

@Serializable
data class UpdateGuessPersonAnswerStatusRequest(
    val status: String
)