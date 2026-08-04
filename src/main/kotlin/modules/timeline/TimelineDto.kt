package com.fathersprophets.backend.modules.timeline

import kotlinx.serialization.Serializable

@Serializable
data class TimelineDto(
    val id: Int,
    val event1: String,
    val event2: String,
    val event3: String,
    val event4: String,
    val event5: String?,
    val event6: String?,
    val event7: String?,
    val event8: String?,
    val event9: String?,
    val event10: String?,
    val correctOrder: List<Int>
)

@Serializable
data class TimelineCreateDto(
    val event1: String,
    val event2: String,
    val event3: String,
    val event4: String,
    val event5: String? = null,
    val event6: String? = null,
    val event7: String? = null,
    val event8: String? = null,
    val event9: String? = null,
    val event10: String? = null,
    val correctOrder: List<Int>
)

@Serializable
data class TimelineUpdateDto(
    val event1: String? = null,
    val event2: String? = null,
    val event3: String? = null,
    val event4: String? = null,
    val event5: String? = null,
    val event6: String? = null,
    val event7: String? = null,
    val event8: String? = null,
    val event9: String? = null,
    val event10: String? = null,
    val correctOrder: List<Int>? = null
)