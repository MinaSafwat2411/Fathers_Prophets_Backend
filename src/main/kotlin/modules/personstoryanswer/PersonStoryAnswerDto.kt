package com.fathersprophets.backend.modules.personstoryanswer


import com.fathersprophets.backend.database.enums.AnswerStatus
import kotlinx.serialization.Serializable

@Serializable
data class PersonStoryAnswerDto(
    val id: Int,
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val status: AnswerStatus,
    val questionId: Int
)

@Serializable
data class PersonStoryAnswerCreateDto(
    val storyId: Int,
    val userId: Int,
    val answered: String,
    val status: AnswerStatus,
    val questionId: Int
)

@Serializable
data class PersonStoryAnswerUpdateDto(
    val storyId: Int? = null,
    val userId: Int? = null,
    val answered: String? = null,
    val status: AnswerStatus? = null,
    val questionId: Int? = null
)