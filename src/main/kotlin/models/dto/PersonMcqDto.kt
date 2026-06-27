package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.McqCorrectAnswer
import com.fathersprophets.backend.models.personmcq.PersonMcqResponse

data class PersonMcqDto(
    val id: Int,
    val questionId: Int,
    val question: String,
    val first: String,
    val second: String,
    val third: String,
    val fourth: String,
    val correctAnswer: McqCorrectAnswer
) {
    fun convertToPersonMcqResponse() = PersonMcqResponse(
        id = this.id,
        questionId = this.questionId,
        question = this.question,
        first = this.first,
        second = this.second,
        third = this.third,
        fourth = this.fourth,
        correctAnswer = this.correctAnswer.name
    )
}