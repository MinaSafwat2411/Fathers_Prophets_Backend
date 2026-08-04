package com.fathersprophets.backend.modules.guesspersonanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import kotlinx.serialization.Serializable

@Serializable
data class GuessPersonAnswerDto(
    val id: Int,
    val questionId: Int,
    val userId: Int,
    val answer: Int,
    val status: AnswerStatus
)

@Serializable
data class GuessPersonAnswerCreateDto(
    val questionId: Int,
    val userId: Int,
    val answer: Int,
    val status: AnswerStatus = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
)

@Serializable
data class GuessPersonAnswerUpdateDto(
    val questionId: Int? = null,
    val userId: Int? = null,
    val answer: Int? = null,
    val status: AnswerStatus? = null
)