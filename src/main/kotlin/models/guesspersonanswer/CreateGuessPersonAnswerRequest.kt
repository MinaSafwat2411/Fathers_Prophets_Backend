package com.fathersprophets.backend.models.guesspersonanswer

import kotlinx.serialization.Serializable

@Serializable
data class CreateGuessPersonAnswerRequest(
    val questionId: Int,
    val userId: Int,
    val personId: Int
)