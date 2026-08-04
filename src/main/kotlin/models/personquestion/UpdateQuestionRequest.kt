package com.fathersprophets.backend.models.personquestion

import com.fathersprophets.backend.modules.person.complete.QuestionType
import com.fathersprophets.backend.models.dto.PersonQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateQuestionRequest(
    val question: String? = null,
    val personId: Int  ? = null,
    val type: String? = null,
    val correctAnswer: String? = null
){
    fun convertToPersonQuestionDto(id: Int) = PersonQuestionDto(
        id = id,
        question = this.question?: "",
        personId = this.personId ?: 0,
        type = try{
            QuestionType.valueOf(this.type ?: "")
        } catch (e: IllegalArgumentException) {
            QuestionType.complete
        },
        correctAnswer = this.correctAnswer?: ""
    )
}
