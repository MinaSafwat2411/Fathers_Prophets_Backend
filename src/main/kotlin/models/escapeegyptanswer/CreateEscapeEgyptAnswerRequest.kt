package com.fathersprophets.backend.models.escapeegyptanswer

import kotlinx.serialization.Serializable

@Serializable
data class CreateEscapeEgyptAnswerRequest(
    val escapeEgyptId: Int,
    val escapeQuestionId: Int,
    val userId: Int,
    val answer: String
)