package com.fathersprophets.backend.models.guesspersonanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.GuessPersonAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateGuessPersonAnswerRequest(
    val questionId: Int,
    val userId: Int,
    val personId: Int,
    val status : String
){
    fun convertToDto(id : Int) = GuessPersonAnswerDto(
        id = id,
        questionId = questionId,
        userId = userId,
        personId = personId,
        status = AnswerStatus.valueOf(status)
    )
}