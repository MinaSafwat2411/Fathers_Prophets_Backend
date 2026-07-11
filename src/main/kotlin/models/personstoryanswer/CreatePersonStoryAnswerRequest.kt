package com.fathersprophets.backend.models.personstoryanswer

import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonStoryAnswerRequest(
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val questionId: Int
)