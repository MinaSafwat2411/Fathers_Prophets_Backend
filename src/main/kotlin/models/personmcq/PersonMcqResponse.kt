package com.fathersprophets.backend.models.personmcq

import kotlinx.serialization.Serializable

@Serializable
data class PersonMcqResponse(
    val id: Int,
    val questionId: Int,
    val question: String,
    val first: String,
    val second: String,
    val third: String,
    val fourth: String,
    val correctAnswer: String
)