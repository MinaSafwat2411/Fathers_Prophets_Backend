package com.fathersprophets.backend.models.personquestion

import com.fathersprophets.backend.modules.person.complete.QuestionType
import com.fathersprophets.backend.models.dto.PersonQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuestionRequest(
    val question: String,
    val personId: Int,
    val type: String,
    val correctAnswer: String
){
    fun convertToPersonQuestionDto() = PersonQuestionDto(
        id = 0,
        question = this.question,
        personId = this.personId,
        type = QuestionType.valueOf(this.type),
        correctAnswer = this.correctAnswer
    )
}
