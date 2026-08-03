package com.fathersprophets.backend.database.tables.escapeegyptquestion


import kotlinx.serialization.Serializable

@Serializable
data class EscapeEgyptQuestionDto(
    val id: Int,
    val escapeEgyptId: Int,
    val question: String,
    val correctAnswer: String
)

@Serializable
data class EscapeEgyptQuestionCreateDto(
    val escapeEgyptId: Int,
    val question: String,
    val correctAnswer: String
)

@Serializable
data class EscapeEgyptQuestionUpdateDto(
    val escapeEgyptId: Int? = null,
    val question: String? = null,
    val correctAnswer: String? = null
)