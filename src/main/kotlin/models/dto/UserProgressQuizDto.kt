package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.userprogressquiz.UserProgressQuizResponse

data class UserProgressQuizDto(
    val id: Int,
    val userId: Int,
    val quizId: Int,
    val dayId: Int,
    val score: Int
) {
    fun convertToResponse() = UserProgressQuizResponse(
        id = id,
        userId = userId,
        quizId = quizId,
        dayId = dayId,
        score = score
    )
}