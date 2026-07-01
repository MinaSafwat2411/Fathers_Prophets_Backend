package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.escapeegyptquestion.EscapeEgyptQuestionResponse

data class EscapeEgyptQuestionDto(
    val id: Int,
    val escapeEgyptId: Int,
    val question: String,
    val correctAnswer: String
) {
    fun convertToResponse() = EscapeEgyptQuestionResponse(
        id = id,
        escapeEgyptId = escapeEgyptId,
        question = question,
        correctAnswer = correctAnswer
    )
}