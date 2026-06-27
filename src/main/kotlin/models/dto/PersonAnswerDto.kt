package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.AnswerStatus
import com.fathersprophets.backend.models.personanswer.PersonAnswerResponse

data class PersonAnswerDto(
    val id: Int,
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: AnswerStatus
) {
    fun convertToPersonAnswerResponse() = PersonAnswerResponse(
        id = this.id,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = this.status.name
    )
}