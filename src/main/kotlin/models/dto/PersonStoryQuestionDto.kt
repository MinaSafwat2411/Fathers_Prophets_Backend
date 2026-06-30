package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.personstoryquestion.PersonStoryQuestionResponse

data class PersonStoryQuestionDto(
    val id: Int,
    val storyId: Int,
    val question: String
) {
    fun convertToPersonStoryQuestionResponse() = PersonStoryQuestionResponse(
        id = this.id,
        storyId = this.storyId,
        question = this.question
    )
}