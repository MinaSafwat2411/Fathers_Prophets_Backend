package com.fathersprophets.backend.database.repository.superevent

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.superevent.SuperEventAvailabilityResponse
import com.fathersprophets.backend.models.superevent.SuperEventRequest
import com.fathersprophets.backend.models.superevent.SuperEventResponse

interface ISuperEventRepository {
    fun getAllSuperEvents(lang: String): ApiResponse<List<SuperEventResponse>>
    fun getSuperEventById(id: Int, lang: String): ApiResponse<SuperEventResponse>
    fun getUpcomingSuperEvents(lang: String): ApiResponse<List<SuperEventResponse>>
    fun createSuperEvent(request: SuperEventRequest, lang: String): ApiResponse<Int>
    fun updateSuperEvent(id: Int, request: SuperEventRequest, lang: String): ApiResponse<Nothing>
    fun deleteSuperEvent(id: Int, lang: String): ApiResponse<Nothing>
    fun getSuperEventAvailability(id: Int, lang: String): ApiResponse<SuperEventAvailabilityResponse>
}