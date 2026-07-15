package com.fathersprophets.backend.models.personquestion

import kotlinx.serialization.Serializable

@Serializable
data class PersonQuestionResponse(
    val id: Int,
    val question: String,
    val personId: Int,
    val type: String,
    val correctAnswer: String? = null
)
