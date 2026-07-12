package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.personstoryanswer.PersonStoryAnswerResponse

data class PersonStoryAnswerDto(
    val id: Int,
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val status: AnswerStatus,
    val questionId: Int
) {
    fun convertToResponse() = PersonStoryAnswerResponse(
        id = id,
        storyId = storyId,
        userId = userId,
        answered = answered,
        status = status.name,
        questionId = questionId
    )
}