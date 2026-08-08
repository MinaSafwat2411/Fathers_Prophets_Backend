package com.fathersprophets.backend.database.tables.quiz


import kotlinx.serialization.Serializable

@Serializable
data class QuizDto(
    val id: Int,
    val number: Int,
    val startAt: String,
    val endAt: String,
    val title: String,
    val familyId: Int
)

@Serializable
data class QuizCreateDto(
    val number: Int,
    val startAt: String,
    val endAt: String,
    val title: String,
    val familyId: Int
)

@Serializable
data class QuizUpdateDto(
    val number: Int? = null,
    val startAt: String? = null,
    val endAt: String? = null,
    val title: String? = null,
    val familyId: Int? = null
)