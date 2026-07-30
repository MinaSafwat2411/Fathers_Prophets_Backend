package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.complete.QuestionType
import com.fathersprophets.backend.models.personquestion.PersonQuestionResponse

data class PersonQuestionDto(
    val id: Int,
    val question: String,
    val personId: Int,
    val type: QuestionType,
    val correctAnswer : String,
){
    fun convertToPersonQuestionResponse(correctAnswer: String? = null) = PersonQuestionResponse(
        id = this.id,
        question = this.question,
        personId = this.personId,
        type = this.type.name,
        correctAnswer = correctAnswer
    )
}
