package com.fathersprophets.backend.models.quiz

import com.fathersprophets.backend.models.dto.QuizDto
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class CreateQuizRequest(
    val number: Int,
    val startAt: String,
    val endAt: String
) {
    fun convertToDto() = QuizDto(
        id = 0,
        number = number,
        startAt = Instant.parse(startAt),
        endAt = Instant.parse(endAt)
    )
}