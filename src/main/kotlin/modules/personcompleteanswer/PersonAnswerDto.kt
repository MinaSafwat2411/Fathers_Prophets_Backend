package com.fathersprophets.backend.modules.personcompleteanswer


import com.fathersprophets.backend.database.enums.AnswerStatus
import kotlinx.serialization.Serializable

@Serializable
data class PersonAnswerDto(
    val id: Int,
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: AnswerStatus
)

@Serializable
data class PersonAnswerCreateDto(
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: AnswerStatus = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
)

@Serializable
data class PersonAnswerUpdateDto(
    val answer: String? = null,
    val questionId: Int? = null,
    val userId: Int? = null,
    val status: AnswerStatus? = null
)