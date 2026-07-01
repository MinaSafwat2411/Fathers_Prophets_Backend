package com.fathersprophets.backend.models.timeline

import com.fathersprophets.backend.models.dto.TimelineDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateTimelineRequest(
    val event1: String,
    val event2: String,
    val event3: String,
    val event4: String,
    val correctOrder: List<Int>
) {
    fun convertToDto() = TimelineDto(
        id = 0,
        event1 = event1,
        event2 = event2,
        event3 = event3,
        event4 = event4,
        correctOrder = correctOrder
    )
}