package com.fathersprophets.backend.modules.userprogress

import kotlinx.serialization.Serializable

@Serializable
data class UserProgressQuizDto(
    val id: Int,
    val userId: Int,
    val quizId: Int,
    val dayId: Int,
    val score: Int
)

@Serializable
data class UserProgressQuizCreateDto(
    val userId: Int,
    val quizId: Int,
    val dayId: Int,
    val score: Int = 0
)

@Serializable
data class UserProgressQuizUpdateDto(
    val userId: Int? = null,
    val quizId: Int? = null,
    val dayId: Int? = null,
    val score: Int? = null
)