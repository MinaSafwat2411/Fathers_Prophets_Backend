package com.fathersprophets.backend.services.events.eventmember

import com.fathersprophets.backend.database.repository.events.eventmember.IEventMemberRepository
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.eventmember.EventMemberBulkRequest
import com.fathersprophets.backend.models.eventmember.EventMemberRequest
import com.fathersprophets.backend.models.eventmember.EventMemberResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class EventMemberService(
    private val eventMemberRepository: IEventMemberRepository
) : IEventMemberService {
    override fun addEventMember(eventMember: EventMemberRequest, lang: String): ApiResponse<EventMemberResponse> {
        validateRequired(
            eventMember.eventId to "event_id",
            eventMember.userId to "user_id",
            eventMember.name to "name",
            eventMember.eventType to "event_type",
            lang = lang
        )
        return eventMemberRepository.addEventMember(eventMember, lang)
    }

    override fun addEventMembersBulk(
        request: EventMemberBulkRequest,
        lang: String
    ): ApiResponse<List<EventMemberResponse>> {
        val members = request.resolvedMembers()

        if (members.isEmpty()) {
            throw BadRequestException(Localization.get("event_members_required", lang))
        }

        members.forEachIndexed { index, member ->
            validateRequired(
                member.eventId to "members[$index].event_id",
                member.userId to "members[$index].user_id",
                member.name to "members[$index].name",
                member.eventType to "members[$index].event_type",
                lang = lang
            )
        }

        // One user cannot join the same event twice, so reject it before the batch hits the unique index.
        if (members.distinctBy { it.eventId to it.userId }.size != members.size) {
            throw BadRequestException(Localization.get("event_members_duplicate", lang))
        }

        return eventMemberRepository.addEventMembersBulk(members, lang)
    }

    override fun deleteEventMember(eventId: Int?, lang: String): ApiResponse<Nothing> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        return eventMemberRepository.deleteEventMember(eventId, lang)
    }

    override fun getEventMembersByEventId(eventId: Int?, lang: String): ApiResponse<List<EventMemberResponse>> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        return eventMemberRepository.getEventMembersByEventId(eventId, lang)
    }

    override fun getEventMembersByUserId(userId: Int?, lang: String): ApiResponse<List<EventMemberResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return eventMemberRepository.getEventMembersByUserId(userId, lang)
    }
}