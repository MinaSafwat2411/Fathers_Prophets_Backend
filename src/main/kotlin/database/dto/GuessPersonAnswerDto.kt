package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.guesspersonanswer.GuessPersonAnswerResponse

data class GuessPersonAnswerDto(
    val id: Int,
    val questionId: Int,
    val userId: Int,
    val personId: Int,
    val status: AnswerStatus
) {
    fun convertToResponse() = GuessPersonAnswerResponse(
        id = id,
        questionId = questionId,
        userId = userId,
        personId = personId,
        status = status.name
    )
}