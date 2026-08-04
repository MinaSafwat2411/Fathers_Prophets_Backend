package com.fathersprophets.backend.modules.quizday

import com.fathersprophets.backend.database.enums.DayOfWeek
import com.fathersprophets.backend.database.enums.QuizDayType
import kotlinx.serialization.Serializable

@Serializable
data class QuizDayDto(
    val id: Int,
    val quizId: Int,
    val dayName: DayOfWeek,
    val book: String,
    val chapter: Int,
    val verseFrom: Int,
    val verseTo: Int,
    val typeDay: QuizDayType
)

@Serializable
data class QuizDayCreateDto(
    val quizId: Int,
    val dayName: DayOfWeek,
    val book: String,
    val chapter: Int,
    val verseFrom: Int,
    val verseTo: Int,
    val typeDay: QuizDayType
)

@Serializable
data class QuizDayUpdateDto(
    val quizId: Int? = null,
    val dayName: DayOfWeek? = null,
    val book: String? = null,
    val chapter: Int? = null,
    val verseFrom: Int? = null,
    val verseTo: Int? = null,
    val typeDay: QuizDayType? = null
)