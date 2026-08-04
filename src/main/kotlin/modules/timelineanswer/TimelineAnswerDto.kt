package com.fathersprophets.backend.modules.timelineanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import kotlinx.serialization.Serializable

@Serializable
data class TimelineAnswerDto(
    val id: Int,
    val timelineId: Int,
    val userId: Int,
    val order: List<Int>,
    val status: AnswerStatus
)

@Serializable
data class TimelineAnswerCreateDto(
    val timelineId: Int,
    val userId: Int,
    val order: List<Int>,
    val status: AnswerStatus
)

@Serializable
data class TimelineAnswerUpdateDto(
    val timelineId: Int? = null,
    val userId: Int? = null,
    val order: List<Int>? = null,
    val status: AnswerStatus? = null
)