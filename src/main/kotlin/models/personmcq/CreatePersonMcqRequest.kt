package com.fathersprophets.backend.models.personmcq

import com.fathersprophets.backend.database.tables.McqCorrectAnswer
import com.fathersprophets.backend.models.dto.PersonMcqDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonMcqRequest(
    val questionId: Int,
    val question: String,
    val first: String,
    val second: String,
    val third: String,
    val fourth: String,
    val correctAnswer: String
) {
    fun convertToPersonMcqDto() = PersonMcqDto(
        id = 0,
        questionId = this.questionId,
        question = this.question,
        first = this.first,
        second = this.second,
        third = this.third,
        fourth = this.fourth,
        correctAnswer = McqCorrectAnswer.valueOf(this.correctAnswer)
    )
}