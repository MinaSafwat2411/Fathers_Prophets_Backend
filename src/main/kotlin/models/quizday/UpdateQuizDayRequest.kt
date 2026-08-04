package com.fathersprophets.backend.models.quizday

import com.fathersprophets.backend.modules.quiz.DayOfWeek
import com.fathersprophets.backend.modules.quiz.QuizDayType
import com.fathersprophets.backend.models.dto.QuizDayDto
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class UpdateQuizDayRequest(
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
    fun convertToDto(id: Int) = QuizDayDto(
        id = id,
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