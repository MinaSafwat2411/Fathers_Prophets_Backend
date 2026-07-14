package com.fathersprophets.backend.database.repository.events

import com.fathersprophets.backend.database.tables.event.EventType
import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.EventResponse
import com.fathersprophets.backend.models.event.UpdateEventRequest

interface IEventRepository {
    fun getAllEvents(lang : String): ApiResponse<List<EventResponse>>
    fun addEvent(event : CreateEventRequest, lang: String): ApiResponse<EventResponse>
    fun updateEvent(eventId : Int, update: UpdateEventRequest, lang: String): ApiResponse<EventResponse>
    fun deleteEvent(userRole: UserRole, eventId: Int, lang: String): ApiResponse<Nothing>
    fun getEventsCount(lang: String): ApiResponse<EventCountsResponse>
    fun getUpcomingEvents(lang: String): ApiResponse<List<EventResponse>>
    fun getEventByEventType(eventType: EventType, lang: String) : ApiResponse<List<EventResponse>>
}