package com.fathersprophets.backend.models.personanswer

import kotlinx.serialization.Serializable

@Serializable
data class PersonAnswerResponse(
    val id: Int,
    val answer: String,
    val questionId: Int,
    val userId: Int,
    val status: String
)