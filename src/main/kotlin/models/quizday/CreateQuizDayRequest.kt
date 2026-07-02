package com.fathersprophets.backend.models.quizday

import com.fathersprophets.backend.database.tables.DayOfWeek
import com.fathersprophets.backend.database.tables.QuizDayType
import com.fathersprophets.backend.models.dto.QuizDayDto
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class CreateQuizDayRequest(
    val quizId: Int,
    val dayName: String,
    val startAt: String,
    val endAt: String,
    val book: String,
    val chapter: Int,
    val verseFrom: Int,
    val verseTo: Int,
    val typeDay: String
) {
    fun convertToDto() = QuizDayDto(
        id = 0,
        quizId = quizId,
        dayName = DayOfWeek.valueOf(dayName),
        startAt = Instant.parse(startAt),
        endAt = Instant.parse(endAt),
        book = book,
        chapter = chapter,
        verseFrom = verseFrom,
        verseTo = verseTo,
        typeDay = QuizDayType.valueOf(typeDay)
    )
}