package com.fathersprophets.backend.models.guessperson

import com.fathersprophets.backend.modules.person.mcq.McqCorrectAnswer
import com.fathersprophets.backend.models.dto.GuessPersonQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateGuessPersonQuestionRequest(
    val question: String,
    val correctPersonId: Int,
    val difficulty: Int?,
    val first: GuessPersonChoice,
    val second: GuessPersonChoice,
    val third: GuessPersonChoice,
    val fourth: GuessPersonChoice,
    val correctAnswer: String
) {
    fun convertToDto() = GuessPersonQuestionDto(
        id = 0,
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