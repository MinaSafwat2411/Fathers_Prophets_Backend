package com.fathersprophets.backend.models.escapeegyptquestion

import com.fathersprophets.backend.database.dto.EscapeEgyptQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateEscapeEgyptQuestionRequest(
    val escapeEgyptId: Int,
    val question: String,
    val correctAnswer: String
) {
    fun convertToDto() = EscapeEgyptQuestionDto(
        id = 0,
        escapeEgyptId = escapeEgyptId,
        question = question,
        correctAnswer = correctAnswer
    )
}