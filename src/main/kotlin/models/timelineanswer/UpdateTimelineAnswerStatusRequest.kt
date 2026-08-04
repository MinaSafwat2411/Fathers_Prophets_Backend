package com.fathersprophets.backend.models.timelineanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.TimelineAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTimelineAnswerStatusRequest(
    val status: String
){
    fun convertToDto(id: Int) = TimelineAnswerDto(
        id = id,
        timelineId = 0,
        userId = 0,
        status = AnswerStatus.valueOf(status)
    )
}