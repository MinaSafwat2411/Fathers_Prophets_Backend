package com.fathersprophets.backend.modules.personmcqanswer


import com.fathersprophets.backend.database.enums.AnswerStatus
import kotlinx.serialization.Serializable

@Serializable
data class PersonMcqAnswerDto(
    val id: Int,
    val answer: Int,
    val questionId: Int,
    val userId: Int,
    val status: AnswerStatus
)

@Serializable
data class PersonMcqAnswerCreateDto(
    val answer: Int,
    val questionId: Int,
    val userId: Int,
    val status: AnswerStatus
)

@Serializable
data class PersonMcqAnswerUpdateDto(
    val answer: Int? = null,
    val questionId: Int? = null,
    val userId: Int? = null,
    val status: AnswerStatus? = null
)