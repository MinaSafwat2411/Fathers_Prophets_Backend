package com.fathersprophets.backend.modules.guessperson

import com.fathersprophets.backend.database.enums.DifficultyType
import kotlinx.serialization.Serializable

@Serializable
data class GuessPersonDto(
    val id: Int,
    val question: String,
    val correctPersonId: Int,
    val difficulty: DifficultyType,
    val first: Int,
    val second: Int,
    val third: Int,
    val fourth: Int,
    val correctAnswer: Int
)

@Serializable
data class GuessPersonCreateDto(
    val question: String,
    val correctPersonId: Int,
    val difficulty: DifficultyType,
    val first: Int,
    val second: Int,
    val third: Int,
    val fourth: Int,
    val correctAnswer: Int
)

@Serializable
data class GuessPersonUpdateDto(
    val question: String? = null,
    val correctPersonId: Int? = null,
    val difficulty: DifficultyType? = null,
    val first: Int? = null,
    val second: Int? = null,
    val third: Int? = null,
    val fourth: Int? = null,
    val correctAnswer: Int? = null
)