package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.AnswerStatus
import com.fathersprophets.backend.models.personmcqanswer.PersonMcqAnswerResponse

data class PersonMcqAnswerDto(
    val id: Int,
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: AnswerStatus
) {
    fun convertToPersonMcqAnswerResponse() = PersonMcqAnswerResponse(
        id = this.id,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = this.status.name
    )
}