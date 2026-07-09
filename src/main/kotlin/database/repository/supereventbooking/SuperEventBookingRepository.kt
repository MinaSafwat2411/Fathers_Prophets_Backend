package com.fathersprophets.backend.database.repository.supereventbooking

import com.fathersprophets.backend.database.dao.SuperEventBookingDao
import com.fathersprophets.backend.database.dao.SuperEventDao
import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.tables.SuperEventBookingStatus
import com.fathersprophets.backend.database.tables.UserRole
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.SuperEventBookingDto
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingPaymentRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingResponse
import com.fathersprophets.backend.utils.Localization
import java.time.LocalDate

class SuperEventBookingRepository(
    private val superEventBookingDao: SuperEventBookingDao,
    private val superEventDao: SuperEventDao,
    private val userDao: UserDao
) : ISuperEventBookingRepository {

    override fun bookSeat(superEventId: Int, userId: Int, lang: String): ApiResponse<SuperEventBookingResponse> {
        val superEvent = superEventDao.findById(superEventId)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))

        if (LocalDate.now().isAfter(LocalDate.parse(superEvent.lastBookingDate))) {
            throw ConflictException(Localization.get("super_event_booking_closed", lang))
        }

        val existing = superEventBookingDao.findByEventAndUser(superEventId, userId)
        if (existing != null && existing.status != SuperEventBookingStatus.cancelled) {
            throw ConflictException(Localization.get("super_event_already_booked", lang))
        }

        val bookedCount = superEventBookingDao.countByStatus(superEventId, SuperEventBookingStatus.booked)
        val status = if (bookedCount < superEvent.totalSeats) {
            SuperEventBookingStatus.booked
        } else {
            val waitingCount = superEventBookingDao.countByStatus(superEventId, SuperEventBookingStatus.waiting)
            if (waitingCount < superEvent.waitingListLimit) {
                SuperEventBookingStatus.waiting
            } else {
                throw ConflictException(Localization.get("super_event_full", lang))
            }
        }

        val user = userDao.findById(UserDto(id = userId, name = "", username = "", passwordHash = "", role = UserRole.member))
            ?: throw NotFoundException(Localization.get("user_not_found", lang))

        if (existing != null) {
            superEventBookingDao.updateStatus(existing.id, status)
        } else {
            superEventBookingDao.create(
                SuperEventBookingDto(
                    id = 0,
                    superEventId = superEventId,
                    userId = userId,
                    name = user.name,
                    totalPaid = 0,
                    status = status,
                    createdAt = ""
                )
            )
        }

        val booking = superEventBookingDao.findByEventAndUser(superEventId, userId)
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

    override fun cancelBooking(bookingId: Int, userId: Int, lang: String): ApiResponse<Nothing> {
        val booking = superEventBookingDao.findByEventAndUser(bookingId, userId)
            ?: throw NotFoundException(Localization.get("super_event_booking_not_found", lang))

        if (booking.status == SuperEventBookingStatus.cancelled) {
            throw ConflictException(Localization.get("super_event_booking_already_cancelled", lang))
        }

        superEventBookingDao.updateStatus(booking.id, SuperEventBookingStatus.cancelled)

        if (booking.status == SuperEventBookingStatus.booked) {
            superEventBookingDao.findOldestWaiting(superEventId)?.let {
                superEventBookingDao.updateStatus(it.id, SuperEventBookingStatus.booked)
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
            data = superEventBookingDao.findByEventId(superEventId).map { it.convertToResponse() },
            message = Localization.get("super_event_bookings_retrieved_successfully", lang)
        )
    }

    override fun getBookingsByUserId(userId: Int, lang: String): ApiResponse<List<SuperEventBookingResponse>> {
        return ApiResponse(
            success = true,
            data = superEventBookingDao.findByUserId(userId).map { it.convertToResponse() },
            message = Localization.get("super_event_bookings_retrieved_successfully", lang)
        )
    }

    override fun updateBookingPaidAmount(
        paymentRequest: SuperEventBookingPaymentRequest,
        lang: String
    ): ApiResponse<SuperEventBookingResponse> {
        val superEvent = superEventDao.findById(paymentRequest.bookingId?:0)
            ?: throw NotFoundException(Localization.get("super_event_booking_not_found", lang))

        val  teacher = superEvent.teachers
            .find { it.id == paymentRequest.teacherId }
            ?: throw NotFoundException(Localization.get("teacher_not_found", lang))

        superEventBookingDao.updateTotalPaid(paymentRequest.bookingId?:0, paymentRequest.totalPaid?:0)

        return ApiResponse(
            success = true,
            data = superEventBookingDao.findById(paymentRequest.bookingId?:0)?.convertToResponse(),
            message = Localization.get("super_event_booking_paid_updated_successfully", lang)
        )
    }
}