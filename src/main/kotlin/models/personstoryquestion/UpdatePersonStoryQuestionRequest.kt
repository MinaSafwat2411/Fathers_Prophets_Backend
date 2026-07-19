package com.fathersprophets.backend.models.personstoryquestion

import com.fathersprophets.backend.models.dto.PersonStoryQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonStoryQuestionRequest(
    val storyId: Int? = null,
    val question: String? = null,
    val correctAnswer: String? = null
) {
    fun convertToPersonStoryQuestionDto(id: Int) = PersonStoryQuestionDto(
        id = id,
        storyId = this.storyId ?: 0,
        question = this.question ?: "",
        correctAnswer = this.correctAnswer ?: ""
    )
}