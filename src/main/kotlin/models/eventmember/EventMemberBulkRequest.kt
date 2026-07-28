package com.fathersprophets.backend.models.eventmember

import kotlinx.serialization.Serializable

@Serializable
data class EventMemberBulkRequest(
    val eventId: Int? = null,
    val eventType: String? = null,
    val members: List<EventMemberRequest> = emptyList()
) {
    /** A whole list is added to one event, so members fall back to the top level values. */
    fun resolvedMembers() = members.map {
        it.copy(
            eventId = it.eventId ?: eventId,
            eventType = it.eventType ?: eventType
        )
    }
}