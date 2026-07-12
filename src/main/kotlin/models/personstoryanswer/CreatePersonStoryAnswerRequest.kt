package com.fathersprophets.backend.models.personstoryanswer

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.PersonStoryAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonStoryAnswerRequest(
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val questionId: Int
) {
    fun convertToPersonStoryAnswerDto() = PersonStoryAnswerDto(
        id = 0,
        storyId = this.storyId,
        userId = this.userId,
        answered = this.answered,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED,
        questionId = this.questionId
    )
}