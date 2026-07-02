package com.fathersprophets.backend.models.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    val id: Int,
    val number: Int,
    val startAt: String,
    val endAt: String
)