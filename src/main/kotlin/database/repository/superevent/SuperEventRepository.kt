package com.fathersprophets.backend.database.repository.superevent

import com.fathersprophets.backend.database.dao.superevent.SuperEventBookingDao
import com.fathersprophets.backend.database.dao.superevent.SuperEventDao
import com.fathersprophets.backend.database.tables.SuperEventBookingStatus
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.models.ApiResponse
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

    override fun getSuperEventById(id: Int, lang: String): ApiResponse<SuperEventResponse> {
        val superEvent = superEventDao.findById(id)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))
        return ApiResponse(
            success = true,
            data = superEvent.convertToResponse(),
            message = Localization.get("super_event_retrieved_successfully", lang)
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
        val id = superEventDao.create(request.convertToDto(0))
        val created = superEventDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("super_event_created_successfully", lang)
        )
    }

    override fun updateSuperEvent(id: Int, request: SuperEventRequest, lang: String): ApiResponse<SuperEventResponse> {
        superEventDao.findById(id)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))

        superEventDao.update(request.convertToDto(id))
        val updated = superEventDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("super_event_updated_successfully", lang)
        )
    }

    override fun deleteSuperEvent(id: Int, lang: String): ApiResponse<Nothing> {
        superEventDao.findById(id)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))

        superEventDao.delete(id)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("super_event_deleted_successfully", lang)
        )
    }

    override fun getSuperEventAvailability(id: Int, lang: String): ApiResponse<SuperEventAvailabilityResponse> {
        val superEvent = superEventDao.findById(id)
            ?: throw NotFoundException(Localization.get("super_event_not_found", lang))

        val bookedCount = superEventBookingDao.countByStatus(id, SuperEventBookingStatus.booked)
        val waitingCount = superEventBookingDao.countByStatus(id, SuperEventBookingStatus.waiting)
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
}