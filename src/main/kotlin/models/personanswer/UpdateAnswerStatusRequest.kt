package com.fathersprophets.backend.models.personanswer

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAnswerStatusRequest(
    val status: String
)