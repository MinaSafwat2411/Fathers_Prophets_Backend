package com.fathersprophets.backend.models.personmcqanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.PersonMcqAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonMcqAnswerRequest(
    val answer: String,
    val questionId: Int,
    val userId: Int,
) {
    fun convertToPersonMcqAnswerDto() = PersonMcqAnswerDto(
        id = 0,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED,
    )
}