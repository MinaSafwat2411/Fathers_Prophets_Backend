package com.fathersprophets.backend.database.repository.timeline

import com.fathersprophets.backend.database.dao.activity.timeline.TimelineDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.TimelineDto
import com.fathersprophets.backend.models.timeline.CreateTimelineRequest
import com.fathersprophets.backend.models.timeline.TimelineResponse
import com.fathersprophets.backend.models.timeline.UpdateTimelineRequest
import com.fathersprophets.backend.utils.Localization

class TimelineRepository(
    private val dao: TimelineDao
) : ITimelineRepository {

    override fun getAllTimelines(lang: String): ApiResponse<List<TimelineResponse>> {
        val timelines = dao.findAll()
        return ApiResponse(
            success = true,
            data = timelines.map { it.convertToResponse() },
            message = Localization.get("timelines_retrieved_successfully", lang)
        )
    }

    override fun getTimelineById(id: Int, lang: String): ApiResponse<TimelineResponse> {
        val timeline = dao.findById(id)
        return ApiResponse(
            success = true,
            data = timeline?.convertToResponse(),
            message = Localization.get("timeline_retrieved_successfully", lang)
        )
    }

    override fun createTimeline(request: CreateTimelineRequest, lang: String): ApiResponse<TimelineResponse> {
        val id = dao.create(request.convertToDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("timeline_created_successfully", lang)
        )
    }

    override fun updateTimeline(id: Int, request: UpdateTimelineRequest, lang: String): ApiResponse<TimelineResponse> {
        dao.update(request.convertToDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("timeline_updated_successfully", lang)
        )
    }

    override fun deleteTimeline(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("timeline_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = TimelineDto(
        id = id,
        event1 = "",
        event2 = "",
        event3 = "",
        event4 = "",
        correctOrder = emptyList()
    )
}