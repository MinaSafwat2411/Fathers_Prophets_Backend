package com.fathersprophets.backend.models.userprogressquiz

import com.fathersprophets.backend.models.dto.UserProgressQuizDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserProgressQuizRequest(
    val userId: Int,
    val quizId: Int,
    val dayId: Int,
    val score: Int = 0
) {
    fun convertToDto() = UserProgressQuizDto(
        id = 0,
        userId = userId,
        quizId = quizId,
        dayId = dayId,
        score = score
    )
}