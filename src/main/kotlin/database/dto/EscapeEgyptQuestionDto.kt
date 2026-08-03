package com.fathersprophets.backend.database.dto


data class EscapeEgyptQuestionDto(
    val id: Int,
    val escapeEgyptId: Int,
    val question: String,
    val correctAnswer: String
)