package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.timeline.TimelineResponse

data class TimelineDto(
    val id: Int,
    val event1: String,
    val event2: String,
    val event3: String,
    val event4: String,
    val correctOrder: List<Int>
) {
    fun convertToResponse() = TimelineResponse(
        id = id,
        event1 = event1,
        event2 = event2,
        event3 = event3,
        event4 = event4
    )
}