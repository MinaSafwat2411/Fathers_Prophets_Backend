package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.personanswer.PersonAnswerResponse

data class PersonAnswerDto(
    val id: Int,
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: AnswerStatus,
    val correctAnswer: String? = null
) {
    fun convertToPersonAnswerResponse(correctAnswer: String? = null) = PersonAnswerResponse(
        id = this.id,
        answer = this.answer,
        questionId = this.questionId,
        userId = this.userId,
        status = this.status.name,
        correctAnswer = correctAnswer
    )
}