package com.fathersprophets.backend.models.timelineanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.TimelineAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTimelineAnswerRequest(
    val timelineId: Int,
    val userId: Int,
    val order: List<Int>
){
    fun convertToDto(id: Int, status: AnswerStatus) = TimelineAnswerDto(
        id = id,
        timelineId = timelineId,
        userId = userId,
        status =status
    )
}