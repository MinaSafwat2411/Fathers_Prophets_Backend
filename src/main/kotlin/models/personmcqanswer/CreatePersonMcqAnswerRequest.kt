package com.fathersprophets.backend.models.personmcqanswer

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.PersonMcqAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonMcqAnswerRequest(
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val personId : Int
) {
    fun convertToPersonMcqAnswerDto() = PersonMcqAnswerDto(
        id = 0,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED,
        personId = this.personId
    )
}