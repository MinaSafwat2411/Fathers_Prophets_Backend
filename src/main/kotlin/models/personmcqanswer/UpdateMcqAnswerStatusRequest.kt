package com.fathersprophets.backend.models.personmcqanswer

import kotlinx.serialization.Serializable

@Serializable
data class UpdateMcqAnswerStatusRequest(
    val status: String
)