package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.quiz.QuizResponse
import java.time.Instant

data class QuizDto(
    val id: Int,
    val number: Int,
    val startAt: Instant,
    val endAt: Instant
) {
    fun convertToResponse() = QuizResponse(
        id = id,
        number = number,
        startAt = startAt.toString(),
        endAt = endAt.toString()
    )
}