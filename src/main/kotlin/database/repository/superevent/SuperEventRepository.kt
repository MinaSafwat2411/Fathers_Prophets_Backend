package com.fathersprophets.backend.database.repository.superevent

import com.fathersprophets.backend.database.dao.SuperEventBookingDao
import com.fathersprophets.backend.database.dao.SuperEventDao
import com.fathersprophets.backend.database.tables.superevent.SuperEventBookingStatus
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.SuperEventBookingDto
import com.fathersprophets.backend.models.superevent.SuperEventAvailabilityResponse
import com.fathersprophets.backend.models.superevent.SuperEventRequest
import com.fathersprophets.backend.models.superevent.SuperEventResponse
import com.fathersprophets.backend.utils.Localization
import java.time.LocalDate

class SuperEventRepository(
    private val superEventDao: SuperEventDao,
    private val superEventBookingDao: SuperEventBookingDao
) : ISuperEventRepository {

    override fun getAllSuperEvents(lang: String): ApiResponse<List<SuperEventResponse>> {
        return ApiResponse(
            success = true,
            data = superEventDao.findAll().map { it.convertToResponse() },
            message = Localization.get("super_events_retrieved_successfully", lang)
        )
    }

    override fun getUpcomingSuperEvents(lang: String): ApiResponse<List<SuperEventResponse>> {
        return ApiResponse(
            success = true,
            data = superEventDao.findUpcoming().map { it.convertToResponse() },
            message = Localization.get("super_events_retrieved_successfully", lang)
        )
    }

    override fun createSuperEvent(request: SuperEventRequest, lang: String): ApiResponse<SuperEventResponse> {
        val create = superEventDao.create(request.convertToDto(0))
            ?:throw IllegalArgumentException(Localization.get("super_event_creation_failed", lang))
        return ApiResponse(
            success = true,
            data = create.convertToResponse(),
            message = Localization.get("super_event_created_successfully", lang)
        )
    }

    override fun updateSuperEvent(id: Int, request: SuperEventRequest, lang: String): ApiResponse<SuperEventResponse> {

        val updated = superEventDao.update(request.convertToDto(id))
            ?: throw IllegalArgumentException(Localization.get("super_event_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("super_event_updated_successfully", lang)
        )
    }

    override fun deleteSuperEvent(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = superEventDao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("super_event_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("super_event_deleted_successfully", lang)
        )
    }

    override fun getSuperEventAvailability(id: Int, lang: String): ApiResponse<SuperEventAvailabilityResponse> {
        val superEvent = superEventDao.findById(id)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))

        val bookedCount = superEventBookingDao.countByStatus(statusToDto(SuperEventBookingStatus.booked, id))
        val waitingCount = superEventBookingDao.countByStatus(statusToDto(SuperEventBookingStatus.waiting, id))
        val seatsLeft = (superEvent.totalSeats - bookedCount).coerceAtLeast(0)
        val waitingSeatsLeft = (superEvent.waitingListLimit - waitingCount).coerceAtLeast(0)

        return ApiResponse(
            success = true,
            data = SuperEventAvailabilityResponse(
                superEventId = id,
                totalSeats = superEvent.totalSeats,
                bookedCount = bookedCount,
                seatsLeft = seatsLeft,
                waitingListLimit = superEvent.waitingListLimit,
                waitingCount = waitingCount,
                waitingSeatsLeft = waitingSeatsLeft,
                isFull = seatsLeft <= 0 && waitingSeatsLeft <= 0,
                isBookingClosed = LocalDate.now().isAfter(LocalDate.parse(superEvent.lastBookingDate))
            ),
            message = Localization.get("super_event_availability_retrieved_successfully", lang)
        )
    }

    private fun  statusToDto(status: SuperEventBookingStatus,superEventId: Int) = SuperEventBookingDto(
        id = 0,
        status = status,
        superEventId = superEventId ,
        userId = 0,
        name = "",
        totalPaid = 0,
        createdAt = "",
        teacherId = 0,
    )
}