package com.fathersprophets.backend.services.superevent

import com.fathersprophets.backend.database.repository.superevent.ISuperEventRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.superevent.SuperEventAvailabilityResponse
import com.fathersprophets.backend.models.superevent.SuperEventRequest
import com.fathersprophets.backend.models.superevent.SuperEventResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class SuperEventService(
    private val superEventRepository: ISuperEventRepository
) : ISuperEventService {

    override fun getAllSuperEvents(lang: String): ApiResponse<List<SuperEventResponse>> {
        return superEventRepository.getAllSuperEvents(lang)
    }

    override fun getSuperEventById(id: Int?, lang: String): ApiResponse<SuperEventResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        return superEventRepository.getSuperEventById(id, lang)
    }

    override fun getUpcomingSuperEvents(lang: String): ApiResponse<List<SuperEventResponse>> {
        return superEventRepository.getUpcomingSuperEvents(lang)
    }

    override fun createSuperEvent(request: SuperEventRequest, lang: String): ApiResponse<Int> {
        validateRequired(
            request.title to "title",
            request.startDate to "start_date",
            request.endDate to "end_date",
            request.lastBookingDate to "last_booking_date",
            request.totalSeats to "total_seats",
            request.waitingListLimit to "waiting_list_limit",
            request.teachers to "teachers",
            request.description to "description",
            request.location to "location",
            lang = lang
        )
        return superEventRepository.createSuperEvent(request, lang)
    }

    override fun updateSuperEvent(id: Int?, request: SuperEventRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        validateRequired(
            request.title to "title",
            request.startDate to "start_date",
            request.endDate to "end_date",
            request.lastBookingDate to "last_booking_date",
            request.totalSeats to "total_seats",
            request.waitingListLimit to "waiting_list_limit",
            request.teachers to "teachers",
            request.description to "description",
            request.location to "location",
            lang = lang
        )
        return superEventRepository.updateSuperEvent(id, request, lang)
    }

    override fun deleteSuperEvent(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        return superEventRepository.deleteSuperEvent(id, lang)
    }

    override fun getSuperEventAvailability(id: Int?, lang: String): ApiResponse<SuperEventAvailabilityResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        return superEventRepository.getSuperEventAvailability(id, lang)
    }
}