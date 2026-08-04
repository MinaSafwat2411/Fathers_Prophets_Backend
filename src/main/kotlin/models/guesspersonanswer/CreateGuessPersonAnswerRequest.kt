package com.fathersprophets.backend.models.guesspersonanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.GuessPersonAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateGuessPersonAnswerRequest(
    val questionId: Int,
    val userId: Int,
    val personId: Int
){
    fun convertToDto() = GuessPersonAnswerDto(
        id = 0,
        questionId = questionId,
        userId = userId,
        personId = personId,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )
}