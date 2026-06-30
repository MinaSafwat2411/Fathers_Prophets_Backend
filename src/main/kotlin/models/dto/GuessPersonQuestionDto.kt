package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.McqCorrectAnswer
import com.fathersprophets.backend.models.guessperson.GuessPersonChoice
import com.fathersprophets.backend.models.guessperson.GuessPersonQuestionResponse

data class GuessPersonQuestionDto(
    val id: Int,
    val question: String,
    val correctPersonId: Int,
    val difficulty: Int?,
    val first: GuessPersonChoice,
    val second: GuessPersonChoice,
    val third: GuessPersonChoice,
    val fourth: GuessPersonChoice,
    val correctAnswer: McqCorrectAnswer
) {
    fun convertToResponse() = GuessPersonQuestionResponse(
        id = id,
        question = question,
        difficulty = difficulty,
        first = first,
        second = second,
        third = third,
        fourth = fourth,
    )
}