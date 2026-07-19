package com.fathersprophets.backend.services.superevent.supereventbooking

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingPaymentRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse

interface ISuperEventBookingService {
    fun bookSeat(request: SuperEventBookingRequest, lang: String): ApiResponse<SuperEventBookingResponse>
    fun cancelBooking(superEventId: Int?, userId: Int?, lang: String): ApiResponse<SuperEventBookingResponse>

    fun getBookingSeatByUserIdAndEventId(userId: Int?,superEventId: Int?, lang: String): ApiResponse<SuperEventBookingResponse>

    fun getBookingsBySuperEventId(superEventId: Int?, lang: String): ApiResponse<List<SuperEventBookingResponse>>
    fun updateBookingPaidAmount(
        paymentRequest: SuperEventBookingPaymentRequest,
        lang: String
    ): ApiResponse<SuperEventBookingResponse>
}