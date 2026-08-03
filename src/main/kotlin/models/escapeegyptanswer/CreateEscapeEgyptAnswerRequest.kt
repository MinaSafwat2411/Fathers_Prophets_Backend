package com.fathersprophets.backend.models.escapeegyptanswer

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.database.dto.EscapeEgyptAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateEscapeEgyptAnswerRequest(
    val escapeEgyptId: Int,
    val escapeQuestionId: Int,
    val userId: Int,
    val answer: String
){
    fun convertToDto(id : Int = 0) = EscapeEgyptAnswerDto(
        id = id,
        escapeEgyptId = escapeEgyptId,
        escapeQuestionId = escapeQuestionId,
        userId = userId,
        answer = answer,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )
}