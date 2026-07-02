package com.fathersprophets.backend.models.userprogressquiz

import kotlinx.serialization.Serializable

@Serializable
data class UserProgressQuizResponse(
    val id: Int,
    val userId: Int,
    val quizId: Int,
    val dayId: Int,
    val score: Int
)