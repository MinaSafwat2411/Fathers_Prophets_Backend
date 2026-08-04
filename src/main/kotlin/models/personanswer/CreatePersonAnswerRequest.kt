package com.fathersprophets.backend.models.personanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.PersonAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonAnswerRequest(
    val answer: String,
    val questionId: Int,
    val userId: Int
) {
    fun convertToPersonAnswerDto() = PersonAnswerDto(
        id = 0,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )
}