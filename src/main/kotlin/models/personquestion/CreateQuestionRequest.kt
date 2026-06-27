package com.fathersprophets.backend.models.personquestion

import com.fathersprophets.backend.database.tables.QuestionType
import com.fathersprophets.backend.models.dto.PersonQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuestionRequest(
    val question: String,
    val personId: Int,
    val type: String
){
    fun convertToPersonQuestionDto() = PersonQuestionDto(
        id = 0,
        question = this.question,
        personId = this.personId,
        type = QuestionType.valueOf(this.type)
    )
}
