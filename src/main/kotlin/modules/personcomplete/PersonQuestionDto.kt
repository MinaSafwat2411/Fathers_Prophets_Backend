package com.fathersprophets.backend.modules.personcomplete

import kotlinx.serialization.Serializable

@Serializable
data class PersonQuestionDto(
    val id: Int,
    val question: String,
    val personId: Int,
    val correctAnswer: String?
)

@Serializable
data class PersonQuestionCreateDto(
    val question: String,
    val personId: Int,
    val correctAnswer: String? = null
)

@Serializable
data class PersonQuestionUpdateDto(
    val question: String? = null,
    val personId: Int? = null,
    val correctAnswer: String? = null
)