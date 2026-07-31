package com.fathersprophets.backend.database.dto.event

import com.fathersprophets.backend.database.enums.EventType
import java.time.LocalDate

data class EventDto(
    val id: Int,
    val type: EventType,
    val title: String,
    val dateTime: LocalDate,
    val image: String?
)