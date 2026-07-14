package com.fathersprophets.backend.services.events

import com.fathersprophets.backend.database.repository.events.IEventRepository
import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.EventResponse
import com.fathersprophets.backend.models.event.UpdateEventRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class EventService(
    private val eventRepository: IEventRepository
) : IEventService {
    override fun getAllEvents(lang: String): ApiResponse<List<EventResponse>> {
        return eventRepository.getAllEvents(lang)
    }

    override fun addEvent(event: CreateEventRequest, lang: String): ApiResponse<EventResponse> {
        validateRequired(
            event.title to "title",
            event.dateTime to "date_time",
            event.image to "image",
            event.type to "type",
            lang = lang
        )
        return eventRepository.addEvent(event, lang)
    }

    override fun updateEvent(eventId: Int?, update: UpdateEventRequest, lang: String): ApiResponse<EventResponse> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        return eventRepository.updateEvent(eventId, update, lang)
    }

    override fun deleteEvent(userRole: String?, eventId: Int?, lang: String): ApiResponse<Nothing> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        if(userRole.isNullOrEmpty()) throw ForbiddenException(Localization.get("access_denied", lang))
        return eventRepository.deleteEvent(UserRole.valueOf(userRole),eventId, lang)
    }

    override fun getEventsCount(lang: String): ApiResponse<EventCountsResponse> {
        return eventRepository.getEventsCount(lang)
    }

    override fun getUpcomingEvents(lang: String): ApiResponse<List<EventResponse>> {
        return eventRepository.getUpcomingEvents(lang)
    }

    override fun getEventByEventType(
        eventType: String?,
        lang: String
    ): ApiResponse<List<EventResponse>> {
        if (eventType == null) throw IllegalArgumentException(Localization.get("event_type_required", lang))
        return eventRepository.getEventByEventType(EventType.valueOf(eventType), lang)
    }
}