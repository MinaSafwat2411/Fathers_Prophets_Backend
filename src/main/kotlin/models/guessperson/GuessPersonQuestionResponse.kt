package com.fathersprophets.backend.models.guessperson

import kotlinx.serialization.Serializable

@Serializable
data class GuessPersonQuestionResponse(
    val id: Int,
    val question: String,
    val difficulty: Int?,
    val first: GuessPersonChoice,
    val second: GuessPersonChoice,
    val third: GuessPersonChoice,
    val fourth: GuessPersonChoice,
)