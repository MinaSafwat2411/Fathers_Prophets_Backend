package com.fathersprophets.backend.database.repository.supereventbooking

import com.fathersprophets.backend.database.dao.SuperEventBookingDao
import com.fathersprophets.backend.database.dao.SuperEventDao
import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.tables.SuperEventBookingStatus
import com.fathersprophets.backend.database.tables.UserRole
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.SuperEventBookingDto
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingPaymentRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse
import com.fathersprophets.backend.utils.Localization
import java.time.LocalDate

class SuperEventBookingRepository(
    private val superEventBookingDao: SuperEventBookingDao,
    private val superEventDao: SuperEventDao,
    private val userDao: UserDao
) : ISuperEventBookingRepository {

    override fun bookSeat(request: SuperEventBookingRequest, lang: String): ApiResponse<SuperEventBookingResponse> {
        val superEvent = superEventDao.findById(request.superEventId ?: 0)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))

        if (LocalDate.now().isAfter(LocalDate.parse(superEvent.lastBookingDate))) {
            throw ConflictException(Localization.get("super_event_booking_closed", lang))
        }

        val bookingDto = superEventIdToBookingDto(request.superEventId ?: 0, request.userId ?: 0)
        val existing = superEventBookingDao.findByEventAndUser(bookingDto)
        if (existing != null && existing.status != SuperEventBookingStatus.cancelled) {
            throw ConflictException(Localization.get("super_event_already_booked", lang))
        }

        val bookedCount = superEventBookingDao.countByStatus(bookingDto)
        val status = if (bookedCount < superEvent.totalSeats) {
            SuperEventBookingStatus.booked
        } else {
            val waitingCount = superEventBookingDao.countByStatus(bookingDto.copy(status = SuperEventBookingStatus.waiting))
            if (waitingCount < superEvent.waitingListLimit) {
                SuperEventBookingStatus.waiting
            } else {
                throw ConflictException(Localization.get("super_event_full", lang))
            }
        }

        val user = userDao.findById(UserDto(id = request.userId ?: 0, name = "", username = "", passwordHash = "", role = UserRole.member))
            ?: throw NotFoundException(Localization.get("user_not_found", lang))

        if (existing != null) {
            superEventBookingDao.updateStatus(existing.copy(status = status))
        } else {
            superEventBookingDao.create(
                SuperEventBookingDto(
                    id = 0,
                    superEventId = request.superEventId ?: 0,
                    userId = request.userId ?: 0,
                    name = user.name,
                    totalPaid = 0,
                    status = status,
                    createdAt = ""
                )
            )
        }

        val booking = superEventBookingDao.findByEventAndUser(bookingDto)
        val messageKey = if (status == SuperEventBookingStatus.booked) {
            "super_event_seat_booked_successfully"
        } else {
            "super_event_added_to_waiting_list"
        }

        return ApiResponse(
            success = true,
            data = booking?.convertToResponse(),
            message = Localization.get(messageKey, lang)
        )
    }

    override fun cancelBooking(superEventId: Int, userId: Int, lang: String): ApiResponse<Nothing> {
        val bookingDto = superEventIdToBookingDto(superEventId, userId)
        val booking = superEventBookingDao.findByEventAndUser(bookingDto)
            ?: throw NotFoundException(Localization.get("super_event_booking_not_found", lang))

        if (booking.status == SuperEventBookingStatus.cancelled) {
            throw ConflictException(Localization.get("super_event_booking_already_cancelled", lang))
        }

        superEventBookingDao.updateStatus(booking.copy(status = SuperEventBookingStatus.cancelled))

        if (booking.status == SuperEventBookingStatus.booked) {
            superEventBookingDao.findOldestWaiting(superEventIdToBookingDto(superEventId, 0))?.let {
                superEventBookingDao.updateStatus(it.copy(status = SuperEventBookingStatus.booked))
            }
        }

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("super_event_booking_cancelled_successfully", lang)
        )
    }

    override fun getBookingsBySuperEventId(superEventId: Int, lang: String): ApiResponse<List<SuperEventBookingResponse>> {
        return ApiResponse(
            success = true,
            data = superEventBookingDao.findByEventId(superEventIdToBookingDto(superEventId, 0)).map { it.convertToResponse() },
            message = Localization.get("super_event_bookings_retrieved_successfully", lang)
        )
    }

    override fun updateBookingPaidAmount(
        paymentRequest: SuperEventBookingPaymentRequest,
        lang: String
    ): ApiResponse<SuperEventBookingResponse> {
        val bookingDto = paymentRequest.superEventIdToDto()
        val existingBooking = superEventBookingDao.findById(bookingDto)
            ?: throw NotFoundException(Localization.get("super_event_booking_not_found", lang))

        val superEvent = superEventDao.findById(existingBooking.superEventId)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))

        superEvent.teachers.find { it.id == paymentRequest.teacherId }
            ?: throw NotFoundException(Localization.get("teacher_not_found", lang))

        superEventBookingDao.updateTotalPaid(bookingDto.copy(teacherId = paymentRequest.teacherId))

        return ApiResponse(
            success = true,
            data = superEventBookingDao.findById(bookingDto)?.convertToResponse(),
            message = Localization.get("super_event_booking_paid_updated_successfully", lang)
        )
    }

    private fun superEventIdToBookingDto(superEventId: Int, userId: Int) = SuperEventBookingDto(
        id = 0,
        superEventId = superEventId,
        name = "",
        totalPaid = 0,
        status = SuperEventBookingStatus.booked,
        createdAt = "",
        userId = userId
    )
}
