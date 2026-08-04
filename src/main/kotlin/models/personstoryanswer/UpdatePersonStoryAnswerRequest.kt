package com.fathersprophets.backend.models.personstoryanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.PersonStoryAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonStoryAnswerRequest(
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val questionId: Int,
    val status: String
) {
    fun convertToPersonStoryAnswerDto(id: Int) = PersonStoryAnswerDto(
        id = id,
        storyId = this.storyId,
        userId = this.userId,
        answered = this.answered,
        status = AnswerStatus.valueOf(this.status),
        questionId = this.questionId
    )
}