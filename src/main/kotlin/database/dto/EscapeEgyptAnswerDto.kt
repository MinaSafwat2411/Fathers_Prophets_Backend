package com.fathersprophets.backend.database.dto

import com.fathersprophets.backend.database.enums.AnswerStatus


data class EscapeEgyptAnswerDto(
    val id: Int,
    val escapeQuestionId: Int,
    val userId: Int,
    val answer: String,
    val status: AnswerStatus
)