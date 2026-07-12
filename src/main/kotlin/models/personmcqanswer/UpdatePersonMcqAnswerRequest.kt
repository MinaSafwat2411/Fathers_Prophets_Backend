package com.fathersprophets.backend.models.personmcqanswer

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.PersonMcqAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonMcqAnswerRequest(
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: String
) {
    fun convertToPersonMcqAnswerDto(id: Int) = PersonMcqAnswerDto(
        id = id,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = AnswerStatus.valueOf(this.status)
    )
}