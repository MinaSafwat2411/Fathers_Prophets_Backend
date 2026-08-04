package com.fathersprophets.backend.models.timelineanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.TimelineAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateTimelineAnswerRequest(
    val timelineId: Int,
    val userId: Int,
    val order: List<Int>
){
    fun convertToDto() = TimelineAnswerDto(
        id = 0,
        timelineId = timelineId,
        userId = userId,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )
}