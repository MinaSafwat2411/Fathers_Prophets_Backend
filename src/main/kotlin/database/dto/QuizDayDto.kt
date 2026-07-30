package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.quiz.DayOfWeek
import com.fathersprophets.backend.database.tables.quiz.QuizDayType
import com.fathersprophets.backend.models.quizday.QuizDayResponse
import java.time.Instant

data class QuizDayDto(
    val id: Int,
    val quizId: Int,
    val dayName: DayOfWeek,
    val startAt: Instant,
    val endAt: Instant,
    val book: String,
    val chapter: Int,
    val verseFrom: Int,
    val verseTo: Int,
    val typeDay: QuizDayType
) {
    fun convertToResponse() = QuizDayResponse(
        id = id,
        quizId = quizId,
        dayName = dayName.name,
        startAt = startAt.toString(),
        endAt = endAt.toString(),
        book = book,
        chapter = chapter,
        verseFrom = verseFrom,
        verseTo = verseTo,
        typeDay = typeDay.name
    )
}