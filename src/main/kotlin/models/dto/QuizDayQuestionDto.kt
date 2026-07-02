package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.McqCorrectAnswer
import com.fathersprophets.backend.models.quizdayquestion.QuizDayQuestionResponse

data class QuizDayQuestionDto(
    val id: Int,
    val quizDayId: Int,
    val question: String,
    val choice1: String,
    val choice2: String,
    val choice3: String?,
    val choice4: String?,
    val correctAnswer: McqCorrectAnswer
) {
    fun convertToResponse() = QuizDayQuestionResponse(
        id = id,
        quizDayId = quizDayId,
        question = question,
        choice1 = choice1,
        choice2 = choice2,
        choice3 = choice3,
        choice4 = choice4
    )
}