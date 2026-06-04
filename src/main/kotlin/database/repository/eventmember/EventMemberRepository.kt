package com.fathersprophets.backend.database.repository.eventmember

import com.fathersprophets.backend.database.dao.EventMemberDao
import com.fathersprophets.backend.database.tables.EventType
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.EventMemberDto
import com.fathersprophets.backend.models.eventmember.EventMemberRequest
import com.fathersprophets.backend.models.eventmember.EventMemberResponse
import com.fathersprophets.backend.utils.Localization

class EventMemberRepository(
    private val eventMemberDao: EventMemberDao,
) : IEventMemberRepository {
    override fun addEventMember(eventMember: EventMemberRequest, lang: String): ApiResponse<EventMemberResponse> {
        val id = eventMemberDao.addEventMember(eventMember.toEventMemberDto(0))
        val createdEventMember = eventMemberDao.getEventMemberByIdAndEventId(eventMember.toEventMemberDto(id))
        return ApiResponse(
            success = true,
            data = createdEventMember.toEventMemberResponse(),
            message = Localization.get("event_member_created_successfully", lang)
        )
    }

    override fun deleteEventMember(eventId: Int, lang: String): ApiResponse<Nothing> {
        eventMemberDao.deleteEventMember(idToEventDto(eventId))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("event_member_deleted_successfully", lang)
        )
    }

    override fun getEventMembersByEventId(eventId: Int, lang: String): ApiResponse<List<EventMemberResponse>> {
        val list = eventMemberDao.getEventMembersByEventId(eventIdToEventDto(eventId))
        return ApiResponse(
            success = true,
            data = list.map { it.toEventMemberResponse() },
            message = Localization.get("event_members_retrieved_successfully", lang)
        )
    }

    override fun getEventMembersByUserId(userId: Int, lang: String): ApiResponse<List<EventMemberResponse>> {
        val list = eventMemberDao.getEventMembersByUserId(userIdToEventDto(userId))
        return ApiResponse(
            success = true,
            data = list.map { it.toEventMemberResponse() },
            message = Localization.get("event_members_retrieved_successfully", lang)
        )
    }
    
    private fun eventIdToEventDto(id: Int) = EventMemberDto(
        id = 0,
        eventId = id,
        userId = 0,
        name = "",
        eventType = EventType.bible
    )
    
    private fun userIdToEventDto(id: Int) = EventMemberDto(
        id = 0,
        eventId = 0,
        userId = id,
        name = "",
        eventType = EventType.bible
    )

    private fun idToEventDto(id: Int) = EventMemberDto(
        id = id,
        eventId = 0,
        userId = 0,
        name = "",
        eventType = EventType.bible

    )
    
}