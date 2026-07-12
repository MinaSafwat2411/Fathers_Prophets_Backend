package com.fathersprophets.backend.models.quizdayquestion

import com.fathersprophets.backend.database.tables.person.mcq.McqCorrectAnswer
import com.fathersprophets.backend.models.dto.QuizDayQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuizDayQuestionRequest(
    val quizDayId: Int,
    val question: String,
    val choice1: String,
    val choice2: String,
    val choice3: String? = null,
    val choice4: String? = null,
    val correctAnswer: String
) {
    fun convertToDto() = QuizDayQuestionDto(
        id = 0,
        quizDayId = quizDayId,
        question = question,
        choice1 = choice1,
        choice2 = choice2,
        choice3 = choice3,
        choice4 = choice4,
        correctAnswer = McqCorrectAnswer.valueOf(correctAnswer)
    )
}