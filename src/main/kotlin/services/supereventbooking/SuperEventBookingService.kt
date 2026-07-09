package com.fathersprophets.backend.services.supereventbooking

import com.fathersprophets.backend.database.repository.supereventbooking.ISuperEventBookingRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse
import com.fathersprophets.backend.utils.Localization

class SuperEventBookingService(
    private val superEventBookingRepository: ISuperEventBookingRepository
) : ISuperEventBookingService {

    override fun bookSeat(superEventId: Int?, userId: Int?, lang: String): ApiResponse<SuperEventBookingResponse> {
        if (superEventId == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return superEventBookingRepository.bookSeat(superEventId, userId, lang)
    }

    override fun cancelBooking(superEventId: Int?, userId: Int?, lang: String): ApiResponse<Nothing> {
        if (superEventId == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return superEventBookingRepository.cancelBooking(superEventId, userId, lang)
    }

    override fun getBookingsBySuperEventId(superEventId: Int?, lang: String): ApiResponse<List<SuperEventBookingResponse>> {
        if (superEventId == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        return superEventBookingRepository.getBookingsBySuperEventId(superEventId, lang)
    }

    override fun getBookingsByUserId(userId: Int?, lang: String): ApiResponse<List<SuperEventBookingResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return superEventBookingRepository.getBookingsByUserId(userId, lang)
    }
}