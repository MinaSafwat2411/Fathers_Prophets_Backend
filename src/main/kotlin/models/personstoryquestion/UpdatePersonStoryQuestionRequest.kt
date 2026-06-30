package com.fathersprophets.backend.models.personstoryquestion

import com.fathersprophets.backend.models.dto.PersonStoryQuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonStoryQuestionRequest(
    val storyId: Int,
    val question: String
) {
    fun convertToPersonStoryQuestionDto(id: Int) = PersonStoryQuestionDto(
        id = id,
        storyId = this.storyId,
        question = this.question
    )
}