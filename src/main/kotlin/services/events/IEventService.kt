package com.fathersprophets.backend.services.events

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.EventResponse

interface IEventService {
    fun getAllEvents(lang: String): ApiResponse<List<EventResponse>>
    fun getEventById(eventId: Int?, lang: String): ApiResponse<EventResponse>
    fun addEvent(event: CreateEventRequest, lang: String): ApiResponse<EventResponse>
    fun updateEvent(eventId: Int?, update: CreateEventRequest, lang: String): ApiResponse<EventResponse>
    fun deleteEvent(eventId: Int?, lang: String): ApiResponse<Nothing>
    fun getEventsCount(lang: String): ApiResponse<EventCountsResponse>
    fun getUpcomingEvents(lang: String): ApiResponse<List<EventResponse>>
}
