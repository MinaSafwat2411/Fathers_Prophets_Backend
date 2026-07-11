package com.fathersprophets.backend.models.personstoryanswer

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonStoryAnswerRequest(
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val questionId: Int
)