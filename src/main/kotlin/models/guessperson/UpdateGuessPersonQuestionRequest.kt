package com.fathersprophets.backend.models.guessperson

import com.fathersprophets.backend.database.tables.person.mcq.McqCorrectAnswer
import com.fathersprophets.backend.models.dto.GuessPersonQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateGuessPersonQuestionRequest(
    val question: String,
    val correctPersonId: Int,
    val difficulty: Int?,
    val first: GuessPersonChoice,
    val second: GuessPersonChoice,
    val third: GuessPersonChoice,
    val fourth: GuessPersonChoice,
    val correctAnswer: String
) {
    fun convertToDto(id: Int) = GuessPersonQuestionDto(
        id = id,
        question = question,
        correctPersonId = correctPersonId,
        difficulty = difficulty,
        first = first,
        second = second,
        third = third,
        fourth = fourth,
        correctAnswer = McqCorrectAnswer.valueOf(correctAnswer)
    )
}