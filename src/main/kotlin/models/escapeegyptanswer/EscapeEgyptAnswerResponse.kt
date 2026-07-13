package com.fathersprophets.backend.models.escapeegyptanswer

import kotlinx.serialization.Serializable

@Serializable
data class EscapeEgyptAnswerResponse(
    val id: Int,
    val escapeEgyptId: Int,
    val escapeQuestionId: Int,
    val userId: Int,
    val answer: String,
    val status: String,
    val correctAnswer: String
)