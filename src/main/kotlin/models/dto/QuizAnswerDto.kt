package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.AnswerStatus
import com.fathersprophets.backend.models.quizanswer.QuizAnswerResponse

data class QuizAnswerDto(
    val id: Int,
    val quizId: Int,
    val questionId: Int,
    val dayId: Int,
    val userId: Int,
    val answer: String,
    val status: AnswerStatus
) {
    fun convertToResponse() = QuizAnswerResponse(
        id = id,
        quizId = quizId,
        questionId = questionId,
        dayId = dayId,
        userId = userId,
        answer = answer,
        status = status.name
    )
}