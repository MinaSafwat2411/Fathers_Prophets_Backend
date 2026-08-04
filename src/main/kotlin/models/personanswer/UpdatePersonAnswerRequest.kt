package com.fathersprophets.backend.models.personanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.PersonAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonAnswerRequest(
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: String
) {
    fun convertToPersonAnswerDto(id: Int) = PersonAnswerDto(
        id = id,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = AnswerStatus.valueOf(this.status)
    )
}