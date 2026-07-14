package com.fathersprophets.backend.services.events

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.EventResponse
import com.fathersprophets.backend.models.event.UpdateEventRequest

interface IEventService {
    fun getAllEvents(lang: String): ApiResponse<List<EventResponse>>
    fun addEvent(event: CreateEventRequest, lang: String): ApiResponse<EventResponse>
    fun updateEvent(eventId: Int?, update: UpdateEventRequest, lang: String): ApiResponse<EventResponse>
    fun deleteEvent(userRole : String?, eventId: Int?, lang: String): ApiResponse<Nothing>
    fun getEventsCount(lang: String): ApiResponse<EventCountsResponse>
    fun getUpcomingEvents(lang: String): ApiResponse<List<EventResponse>>

    fun getEventByEventType(eventType: String?, lang: String): ApiResponse<List<EventResponse>>
}
