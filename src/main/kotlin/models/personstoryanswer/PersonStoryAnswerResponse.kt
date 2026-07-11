package com.fathersprophets.backend.models.personstoryanswer

import kotlinx.serialization.Serializable

@Serializable
data class PersonStoryAnswerResponse(
    val id: Int,
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val status: String,
    val questionId: Int
)