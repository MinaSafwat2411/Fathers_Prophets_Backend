package com.fathersprophets.backend.models.quizdayquestion

import kotlinx.serialization.Serializable

@Serializable
data class QuizDayQuestionResponse(
    val id: Int,
    val quizDayId: Int,
    val question: String,
    val choice1: String,
    val choice2: String,
    val choice3: String?,
    val choice4: String?
)