package com.fathersprophets.backend.models.personstoryquestion

import com.fathersprophets.backend.models.dto.PersonStoryQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonStoryQuestionRequest(
    val storyId: Int,
    val question: String,
    val correctAnswer : String
) {
    fun convertToPersonStoryQuestionDto() = PersonStoryQuestionDto(
        id = 0,
        storyId = this.storyId,
        question = this.question,
        correctAnswer = this.correctAnswer
    )
}