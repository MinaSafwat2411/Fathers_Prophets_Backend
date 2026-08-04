package com.fathersprophets.backend.modules.personmcq


import com.fathersprophets.backend.database.enums.McqCorrectAnswer
import kotlinx.serialization.Serializable

@Serializable
data class PersonMcqDto(
    val id: Int,
    val personId: Int,
    val question: String,
    val first: String,
    val second: String,
    val third: String,
    val fourth: String,
    val correctAnswer: McqCorrectAnswer
)

@Serializable
data class PersonMcqCreateDto(
    val personId: Int,
    val question: String,
    val first: String,
    val second: String,
    val third: String,
    val fourth: String,
    val correctAnswer: McqCorrectAnswer
)

@Serializable
data class PersonMcqUpdateDto(
    val personId: Int? = null,
    val question: String? = null,
    val first: String? = null,
    val second: String? = null,
    val third: String? = null,
    val fourth: String? = null,
    val correctAnswer: McqCorrectAnswer? = null
)