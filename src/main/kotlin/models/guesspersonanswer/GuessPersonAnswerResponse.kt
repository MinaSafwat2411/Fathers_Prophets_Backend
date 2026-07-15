package com.fathersprophets.backend.models.guesspersonanswer

import com.fathersprophets.backend.models.guessperson.GuessPersonChoice
import kotlinx.serialization.Serializable

@Serializable
data class GuessPersonAnswerResponse(
    val id: Int,
    val questionId: Int,
    val userId: Int,
    val personId: Int,
    val status: String,
    val correctAnswer: GuessPersonChoice? = null
)