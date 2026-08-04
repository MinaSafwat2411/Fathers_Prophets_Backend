package com.fathersprophets.backend.modules.personstoryquestion


import kotlinx.serialization.Serializable

@Serializable
data class PersonStoryQuestionDto(
    val id: Int,
    val storyId: Int,
    val question: String,
    val correctAnswer: String
)

@Serializable
data class PersonStoryQuestionCreateDto(
    val storyId: Int,
    val question: String,
    val correctAnswer: String
)

@Serializable
data class PersonStoryQuestionUpdateDto(
    val storyId: Int? = null,
    val question: String? = null,
    val correctAnswer: String? = null
)