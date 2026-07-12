package com.fathersprophets.backend.models.quizanswer

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.QuizAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuizAnswerRequest(
    val quizId: Int,
    val questionId: Int,
    val dayId: Int,
    val userId: Int,
    val answer: String
){
    fun convertToDto() = QuizAnswerDto(
        id = 0,
        quizId = this.quizId,
        questionId = this.questionId,
        dayId = this.dayId,
        userId = this.userId,
        answer = this.answer,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )
}