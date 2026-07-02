package com.fathersprophets.backend.models.userprogressquiz

import com.fathersprophets.backend.models.dto.UserProgressQuizDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserProgressQuizRequest(
    val userId: Int,
    val quizId: Int,
    val dayId: Int,
    val score: Int
) {
    fun convertToDto(id: Int) = UserProgressQuizDto(
        id = id,
        userId = userId,
        quizId = quizId,
        dayId = dayId,
        score = score
    )
}