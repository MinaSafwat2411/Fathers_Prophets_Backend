package com.fathersprophets.backend.models.personquestion

import com.fathersprophets.backend.database.tables.QuestionType
import com.fathersprophets.backend.models.dto.PersonQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateQuestionRequest(
    val question: String,
    val personId: Int,
    val type: String
){
    fun convertToPersonQuestionDto(id: Int) = PersonQuestionDto(
        id = id,
        question = this.question,
        personId = this.personId,
        type = QuestionType.valueOf(this.type)
    )
}
