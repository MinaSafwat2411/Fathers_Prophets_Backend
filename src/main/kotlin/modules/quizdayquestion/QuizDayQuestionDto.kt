package com.fathersprophets.backend.modules.quizdayquestion


import com.fathersprophets.backend.database.enums.McqCorrectAnswer
import kotlinx.serialization.Serializable

@Serializable
data class QuizDayQuestionDto(
    val id: Int,
    val quizDayId: Int,
    val question: String,
    val choice1: String,
    val choice2: String,
    val choice3: String?,
    val choice4: String?,
    val correctAnswer: McqCorrectAnswer
)

@Serializable
data class QuizDayQuestionCreateDto(
    val quizDayId: Int,
    val question: String,
    val choice1: String,
    val choice2: String,
    val choice3: String? = null,
    val choice4: String? = null,
    val correctAnswer: McqCorrectAnswer
)

@Serializable
data class QuizDayQuestionUpdateDto(
    val quizDayId: Int? = null,
    val question: String? = null,
    val choice1: String? = null,
    val choice2: String? = null,
    val choice3: String? = null,
    val choice4: String? = null,
    val correctAnswer: McqCorrectAnswer? = null
)