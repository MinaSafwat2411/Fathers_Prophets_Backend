package com.fathersprophets.backend.models.quizanswer

import kotlinx.serialization.Serializable

@Serializable
data class CreateQuizAnswerRequest(
    val quizId: Int,
    val questionId: Int,
    val dayId: Int,
    val userId: Int,
    val answer: String
)