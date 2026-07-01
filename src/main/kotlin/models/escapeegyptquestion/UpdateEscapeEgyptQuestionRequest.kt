package com.fathersprophets.backend.models.escapeegyptquestion

import com.fathersprophets.backend.models.dto.EscapeEgyptQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateEscapeEgyptQuestionRequest(
    val escapeEgyptId: Int,
    val question: String,
    val correctAnswer: String
) {
    fun convertToDto(id: Int) = EscapeEgyptQuestionDto(
        id = id,
        escapeEgyptId = escapeEgyptId,
        question = question,
        correctAnswer = correctAnswer
    )
}