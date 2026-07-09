package com.fathersprophets.backend.services.supereventbooking

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse

interface ISuperEventBookingService {
    fun bookSeat(superEventId: Int?, userId: Int?, lang: String): ApiResponse<SuperEventBookingResponse>
    fun cancelBooking(superEventId: Int?, userId: Int?, lang: String): ApiResponse<Nothing>
    fun getBookingsBySuperEventId(superEventId: Int?, lang: String): ApiResponse<List<SuperEventBookingResponse>>
    fun getBookingsByUserId(userId: Int?, lang: String): ApiResponse<List<SuperEventBookingResponse>>
}