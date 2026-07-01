package com.fathersprophets.backend.models.escapeegyptanswer

import kotlinx.serialization.Serializable

@Serializable
data class UpdateEscapeEgyptAnswerStatusRequest(
    val status: String
)