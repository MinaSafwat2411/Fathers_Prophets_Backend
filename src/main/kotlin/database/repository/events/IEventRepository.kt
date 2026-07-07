package com.fathersprophets.backend.database.repository.events

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.EventCountsResponse
import com.fathersprophets.backend.models.event.EventRequest
import com.fathersprophets.backend.models.event.EventResponse

interface IEventRepository {
    fun getAllEvents(lang : String): ApiResponse<List<EventResponse>>
    fun getEventById(eventId: Int, lang: String): ApiResponse<EventResponse>
    fun addEvent(event : EventRequest, lang: String): ApiResponse<EventResponse>
    fun updateEvent(eventId : Int ,update: EventRequest, lang: String): ApiResponse<EventResponse>
    fun deleteEvent(eventId: Int, lang: String): ApiResponse<Nothing>
    fun getEventsCount(lang: String): ApiResponse<EventCountsResponse>
    fun getUpcomingEvents(lang: String): ApiResponse<List<EventResponse>>
}