package com.fathersprophets.backend.modules.quizanswers

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.database.enums.McqCorrectAnswer
import kotlinx.serialization.Serializable

@Serializable
data class QuizAnswerDto(
    val id: Int,
    val questionId: Int,
    val userId: Int,
    val answer: String,
    val answerOrder: McqCorrectAnswer,
    val status: AnswerStatus
)

@Serializable
data class QuizAnswerCreateDto(
    val questionId: Int,
    val userId: Int,
    val answer: String,
    val answerOrder: McqCorrectAnswer,
    val status: AnswerStatus = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
)

@Serializable
data class QuizAnswerUpdateDto(
    val questionId: Int? = null,
    val userId: Int? = null,
    val answer: String? = null,
    val answerOrder: McqCorrectAnswer? = null,
    val status: AnswerStatus? = null
)