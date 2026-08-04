package com.fathersprophets.backend.modules.escapeegyptanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import kotlinx.serialization.Serializable

@Serializable
data class EscapeEgyptAnswerDto(
    val id: Int,
    val escapeQuestionId: Int,
    val userId: Int,
    val answer: String,
    val status: AnswerStatus
)

@Serializable
data class EscapeEgyptAnswerCreateDto(
    val escapeQuestionId: Int,
    val userId: Int,
    val answer: String,
    val status: AnswerStatus
)

@Serializable
data class EscapeEgyptAnswerUpdateDto(
    val answer: String? = null,
    val status: AnswerStatus? = null
)