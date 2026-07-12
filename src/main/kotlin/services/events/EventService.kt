package com.fathersprophets.backend.services.events

import com.fathersprophets.backend.database.repository.events.IEventRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.EventResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class EventService(
    private val eventRepository: IEventRepository
) : IEventService {
    override fun getAllEvents(lang: String): ApiResponse<List<EventResponse>> {
        return eventRepository.getAllEvents(lang)
    }

    override fun getEventById(eventId: Int?, lang: String): ApiResponse<EventResponse> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        return eventRepository.getEventById(eventId,lang)
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

    override fun updateEvent(eventId: Int?, update: CreateEventRequest, lang: String): ApiResponse<EventResponse> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        return eventRepository.updateEvent(eventId, update, lang)
    }

    override fun deleteEvent(eventId: Int?, lang: String): ApiResponse<Nothing> {
        if (eventId == null) throw IllegalArgumentException(Localization.get("event_id_required", lang))
        return eventRepository.deleteEvent(eventId, lang)
    }

    override fun getEventsCount(lang: String): ApiResponse<EventCountsResponse> {
        return eventRepository.getEventsCount(lang)
    }

    override fun getUpcomingEvents(lang: String): ApiResponse<List<EventResponse>> {
        return eventRepository.getUpcomingEvents(lang)
    }
}