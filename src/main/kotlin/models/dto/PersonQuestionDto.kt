package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.QuestionType
import com.fathersprophets.backend.models.personquestion.PersonQuestionResponse

data class PersonQuestionDto(
    val id: Int,
    val question: String,
    val personId: Int,
    val type: QuestionType
){
    fun convertToPersonQuestionResponse() = PersonQuestionResponse(
        id = this.id,
        question = this.question,
        personId = this.personId,
        type = this.type.name
    )
}
