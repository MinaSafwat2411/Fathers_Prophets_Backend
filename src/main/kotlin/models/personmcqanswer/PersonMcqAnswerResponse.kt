package com.fathersprophets.backend.models.personmcqanswer

import kotlinx.serialization.Serializable

@Serializable
data class PersonMcqAnswerResponse(
    val id: Int,
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: String,
    val correctAnswer : String? = null
)