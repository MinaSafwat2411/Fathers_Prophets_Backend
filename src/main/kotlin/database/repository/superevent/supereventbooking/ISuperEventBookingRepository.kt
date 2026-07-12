package com.fathersprophets.backend.database.repository.superevent.supereventbooking

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingPaymentRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse

interface ISuperEventBookingRepository {
    fun bookSeat(request: SuperEventBookingRequest, lang: String): ApiResponse<SuperEventBookingResponse>
    fun cancelBooking(superEventId: Int, userId: Int, lang: String): ApiResponse<Nothing>
    fun getBookingsBySuperEventId(superEventId: Int, lang: String): ApiResponse<List<SuperEventBookingResponse>>
    fun updateBookingPaidAmount(
        paymentRequest: SuperEventBookingPaymentRequest,
        lang: String
    ): ApiResponse<SuperEventBookingResponse>
}