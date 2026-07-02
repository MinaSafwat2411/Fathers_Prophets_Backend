package com.fathersprophets.backend.models.quiz

import com.fathersprophets.backend.models.dto.QuizDto
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class UpdateQuizRequest(
    val number: Int,
    val startAt: String,
    val endAt: String
) {
    fun convertToDto(id: Int) = QuizDto(
        id = id,
        number = number,
        startAt = Instant.parse(startAt),
        endAt = Instant.parse(endAt)
    )
}