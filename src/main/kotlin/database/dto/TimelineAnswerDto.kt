package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.timelineanswer.TimelineAnswerResponse

data class TimelineAnswerDto(
    val id: Int,
    val timelineId: Int,
    val userId: Int,
    val status: AnswerStatus
) {
    fun convertToResponse() = TimelineAnswerResponse(
        id = id,
        timelineId = timelineId,
        userId = userId,
        status = status.name
    )
}