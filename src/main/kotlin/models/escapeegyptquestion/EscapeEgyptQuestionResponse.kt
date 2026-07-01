package com.fathersprophets.backend.models.escapeegyptquestion

import kotlinx.serialization.Serializable

@Serializable
data class EscapeEgyptQuestionResponse(
    val id: Int,
    val escapeEgyptId: Int,
    val question: String,
    val correctAnswer: String
)