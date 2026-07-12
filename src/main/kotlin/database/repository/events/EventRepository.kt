package com.fathersprophets.backend.database.repository.events

import com.fathersprophets.backend.database.dao.event.EventDao
import com.fathersprophets.backend.database.dao.notification.NotificationDao
import com.fathersprophets.backend.database.tables.EventType
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.EventDto
import com.fathersprophets.backend.models.dto.NotificationDto
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.EventResponse
import com.fathersprophets.backend.models.event.UpdateEventRequest
import com.fathersprophets.backend.services.notification.IFirebaseMessagingService
import com.fathersprophets.backend.utils.Localization

class EventRepository(
    private val eventDao: EventDao,
    private val notificationDao: NotificationDao,
    private val firebaseMessagingService: IFirebaseMessagingService
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
        val event = eventDao.getEventById(eventId)
        return ApiResponse(
            success = true,
            data = event?.convertToEventResponse(),
            message = Localization.get("event_retrieved_successfully", lang)
        )
    }

    override fun addEvent(event: CreateEventRequest, lang: String): ApiResponse<Int> {

        val id = eventDao.addEvent(event.convertToEventDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("event_creation_failed", lang))

        val notificationsId = notificationDao.create(event.convertToNotification(id))

        if (notificationsId == 0) throw IllegalArgumentException(Localization.get("notification_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("event_created_successfully", lang)
        )
    }

    override fun updateEvent(eventId: Int, update: UpdateEventRequest, lang: String): ApiResponse<Nothing> {

        val  update = eventDao.updateEvent(update.convertToEventDto(eventId))

        if (!update) throw IllegalArgumentException(Localization.get("event_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("event_updated_successfully", lang)
        )
    }

    override fun deleteEvent(eventId: Int, lang: String): ApiResponse<Nothing> {
        eventDao.deleteEvent(eventId)
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
        val events = eventDao.getEventByType(eventType)
        return ApiResponse(
            success = true,
            data = events.map { it.convertToEventResponse() },
            message = Localization.get("events_by_type_retrieved_successfully", lang)
        )
    }
}