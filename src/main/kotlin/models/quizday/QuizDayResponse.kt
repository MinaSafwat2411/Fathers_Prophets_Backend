package com.fathersprophets.backend.models.quizday

import kotlinx.serialization.Serializable

@Serializable
data class QuizDayResponse(
    val id: Int,
    val quizId: Int,
    val dayName: String,
    val startAt: String,
    val endAt: String,
    val book: String,
    val chapter: Int,
    val verseFrom: Int,
    val verseTo: Int,
    val typeDay: String
)