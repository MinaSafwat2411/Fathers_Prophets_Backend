package com.fathersprophets.backend.database.repository.events

import com.fathersprophets.backend.database.dao.event.EventDao
import com.fathersprophets.backend.database.dao.notification.NotificationDao
import com.fathersprophets.backend.database.tables.EventType
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.EventDto
import com.fathersprophets.backend.models.dto.NotificationDto
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.EventRequest
import com.fathersprophets.backend.models.event.EventResponse
import com.fathersprophets.backend.utils.Localization

class EventRepository(
    private val eventDao: EventDao,
    private val notificationDao: NotificationDao,
) : IEventRepository {
    override fun getAllEvents(lang: String): ApiResponse<List<EventResponse>> {
        val events = eventDao.getAllEvents()
        return ApiResponse(
            success = true,
            data = events.map { it.convertToEventResponse() },
            message = Localization.get("events_retrieved_successfully", lang)
        )
    }

    override fun getEventById(eventId: Int, lang: String): ApiResponse<EventResponse> {
        val event = eventDao.getEventById(idToDto(eventId))
        return ApiResponse(
            success = true,
            data = event?.convertToEventResponse(),
            message = Localization.get("event_retrieved_successfully", lang)
        )
    }

    override fun addEvent(event: EventRequest, lang: String): ApiResponse<EventResponse> {
        val id = eventDao.addEvent(event.convertToEventDto(0))
        val createdEvent = eventDao.getEventById(idToDto(id))

        if (createdEvent != null) {
            notificationDao.create(
                NotificationDto(
                    id = 0,
                    eventId = createdEvent.id,
                    type = createdEvent.type,
                    title = createdEvent.title,
                    message = Localization.get("new_event_notification_message", lang),
                    isRead = false,
                    createdAt = ""
                )
            )
        }

        return ApiResponse(
            success = true,
            data = createdEvent?.convertToEventResponse(),
            message = Localization.get("event_created_successfully", lang)
        )
    }

    override fun updateEvent(eventId: Int, update: EventRequest, lang: String): ApiResponse<EventResponse> {
        eventDao.updateEvent(update.convertToEventDto(eventId))
        return ApiResponse(
            success = true,
            data = update.convertToEventDto(eventId).convertToEventResponse(),
            message = Localization.get("event_updated_successfully", lang)
        )
    }

    override fun deleteEvent(eventId: Int, lang: String): ApiResponse<Nothing> {
        eventDao.deleteEvent(idToDto(eventId))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("event_deleted_successfully", lang)
        )
    }

    override fun getEventsCount(lang: String): ApiResponse<EventCountsResponse> {
        val eventCounts = eventDao.getEventsCount()
        return ApiResponse(
            success = true,
            data = eventCounts.convertToEventCountResponse(),
            message = Localization.get("event_counts_retrieved_successfully", lang)
        )
    }

    override fun getUpcomingEvents(lang: String): ApiResponse<List<EventResponse>> {
        val events = eventDao.getUpcomingEvents()
        return ApiResponse(
            success = true,
            data = events.map { it.convertToEventResponse() },
            message = Localization.get("upcoming_events_retrieved_successfully", lang)
        )
    }

    override fun getEventByEventType(eventType: EventType, lang: String): ApiResponse<List<EventResponse>> {
        val events = eventDao.getEventByType(idToDto(eventType.ordinal))
        return ApiResponse(
            success = true,
            data = events.map { it.convertToEventResponse() },
            message = Localization.get("events_by_type_retrieved_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = EventDto(
        id = id,
        title = "",
        dateTime = "",
        image = "",
        type = EventType.bible
    )


}