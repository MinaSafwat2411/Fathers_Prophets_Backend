package com.fathersprophets.backend.database.repository.events.eventmember

import com.fathersprophets.backend.database.dao.event.EventMemberDao
import com.fathersprophets.backend.database.tables.EventType
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.EventMemberDto
import com.fathersprophets.backend.models.eventmember.EventMemberRequest
import com.fathersprophets.backend.models.eventmember.EventMemberResponse
import com.fathersprophets.backend.utils.Localization

class EventMemberRepository(
    private val eventMemberDao: EventMemberDao,
) : IEventMemberRepository {
    override fun addEventMember(eventMember: EventMemberRequest, lang: String): ApiResponse<Int> {
        val id = eventMemberDao.addEventMember(eventMember.toEventMemberDto(0))

        if (id == 0) throw IllegalArgumentException(Localization.get("event_member_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("event_member_created_successfully", lang)
        )
    }

    override fun deleteEventMember(eventId: Int, lang: String): ApiResponse<Nothing> {
        val deleted = eventMemberDao.deleteEventMember(eventId)

        if (!deleted) throw IllegalArgumentException(Localization.get("event_member_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("event_member_deleted_successfully", lang)
        )
    }

    override fun getEventMembersByEventId(eventId: Int, lang: String): ApiResponse<List<EventMemberResponse>> {
        val list = eventMemberDao.getEventMembersByEventId(eventId)
        return ApiResponse(
            success = true,
            data = list.map { it.toEventMemberResponse() },
            message = Localization.get("event_members_retrieved_successfully", lang)
        )
    }

    override fun getEventMembersByUserId(userId: Int, lang: String): ApiResponse<List<EventMemberResponse>> {

        val list = eventMemberDao.getEventMembersByUserId(userId)
        return ApiResponse(
            success = true,
            data = list.map { it.toEventMemberResponse() },
            message = Localization.get("event_members_retrieved_successfully", lang)
        )
    }
}