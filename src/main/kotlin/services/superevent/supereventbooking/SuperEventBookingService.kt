package com.fathersprophets.backend.services.superevent.supereventbooking

import com.fathersprophets.backend.database.repository.superevent.supereventbooking.ISuperEventBookingRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingPaymentRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class SuperEventBookingService(
    private val superEventBookingRepository: ISuperEventBookingRepository
) : ISuperEventBookingService {

    override fun bookSeat(request: SuperEventBookingRequest, lang: String): ApiResponse<SuperEventBookingResponse> {
        validateRequired(
            request.superEventId to "superEventId",
            request.userId to "userId",
            lang = lang
        )
        return superEventBookingRepository.bookSeat(request, lang)
    }

    override fun cancelBooking(superEventId: Int?, userId: Int?, lang: String): ApiResponse<SuperEventBookingResponse> {
        if (superEventId == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return superEventBookingRepository.cancelBooking(superEventId, userId, lang)
    }

    override fun getBookingSeatByUserIdAndEventId(
        userId: Int?,
        superEventId: Int?,
        lang: String
    ): ApiResponse<SuperEventBookingResponse> {
        if (superEventId == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return superEventBookingRepository.getBookingSeatByUserIdAndEventId(userId, superEventId, lang)
    }

    override fun getBookingsBySuperEventId(superEventId: Int?, lang: String): ApiResponse<List<SuperEventBookingResponse>> {
        if (superEventId == null) throw IllegalArgumentException(Localization.get("super_event_id_required", lang))
        return superEventBookingRepository.getBookingsBySuperEventId(superEventId, lang)
    }


    override fun updateBookingPaidAmount(
        paymentRequest: SuperEventBookingPaymentRequest,
        lang: String
    ): ApiResponse<SuperEventBookingResponse> {
        validateRequired(
            paymentRequest.bookingId to "bookingId",
            paymentRequest.totalPaid to "totalPaid",
            lang = lang
        )
        return superEventBookingRepository.updateBookingPaidAmount(paymentRequest, lang)
    }
}