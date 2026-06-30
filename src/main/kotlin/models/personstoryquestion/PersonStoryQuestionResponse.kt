package com.fathersprophets.backend.models.personstoryquestion

import kotlinx.serialization.Serializable

@Serializable
data class PersonStoryQuestionResponse(
    val id: Int,
    val storyId: Int,
    val question: String
)