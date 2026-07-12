package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.escapeegyptanswer.EscapeEgyptAnswerResponse

data class EscapeEgyptAnswerDto(
    val id: Int,
    val escapeEgyptId: Int,
    val escapeQuestionId: Int,
    val userId: Int,
    val answer: String,
    val status: AnswerStatus
) {
    fun convertToResponse() = EscapeEgyptAnswerResponse(
        id = id,
        escapeEgyptId = escapeEgyptId,
        escapeQuestionId = escapeQuestionId,
        userId = userId,
        answer = answer,
        status = status.name
    )
}