package com.fathersprophets.backend.database.repository.events

import com.fathersprophets.backend.database.dao.event.EventDao
import com.fathersprophets.backend.database.dao.NotificationDao
import com.fathersprophets.backend.database.dao.users.UserDao
import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.EventResponse
import com.fathersprophets.backend.models.event.UpdateEventRequest
import com.fathersprophets.backend.services.notification.IFirebaseMessagingService
import com.fathersprophets.backend.utils.Localization

class EventRepository(
    private val eventDao: EventDao,
    private val notificationDao: NotificationDao,
    private val userDao: UserDao,
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

    override fun addEvent(event: CreateEventRequest, lang: String): ApiResponse<EventResponse> {

        val createdEvent = eventDao.addEvent(event.convertToEventDto())
            ?: throw IllegalArgumentException(Localization.get("event_creation_failed", lang))

        val notificationsId = notificationDao.create(event.convertToNotification(createdEvent.id))
            ?: throw IllegalArgumentException(Localization.get("notification_creation_failed", lang))

        firebaseMessagingService.sendToTokens(
            tokens = userDao.findAllFcmTokens(),
            title = event.type?: "",
            body = event.title ?: "",
            data = mapOf(
                "eventId" to createdEvent.id.toString(),
                "notificationId" to notificationsId.toString(),
                "type" to "event",
                "eventType" to event.type.toString(),
                "lang" to lang,
                "eventTitle" to event.title.toString(),
                "eventDateTime" to event.dateTime.toString(),
                "eventImage" to event.image.toString()
            )
        )

        return ApiResponse(
            success = true,
            data = createdEvent.convertToEventResponse(),
            message = Localization.get("event_created_successfully", lang)
        )
    }

    override fun updateEvent(eventId: Int, update: UpdateEventRequest, lang: String): ApiResponse<EventResponse> {

        val  update = eventDao.updateEvent(update.convertToEventDto(eventId))
            ?:throw IllegalArgumentException(Localization.get("event_update_failed", lang))

        return ApiResponse(
            success = true,
            data = update.convertToEventResponse(),
            message = Localization.get("event_updated_successfully", lang)
        )
    }

    override fun deleteEvent(userRole: UserRole, eventId: Int, lang: String): ApiResponse<Nothing> {
        val event = eventDao.getEventById(eventId)
            ?: throw IllegalArgumentException(Localization.get("event_delete_failed", lang))

        if (userRole == UserRole.admin || userRole == UserRole.superadmin ||(event.type.name == userRole.name)) {
            eventDao.deleteEvent(eventId)
            notificationDao.deleteByEventId(eventId)
        } else {
            throw IllegalArgumentException(Localization.get("access_denied", lang))
        }
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