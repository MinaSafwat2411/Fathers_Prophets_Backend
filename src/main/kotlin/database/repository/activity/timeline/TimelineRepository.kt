package com.fathersprophets.backend.database.repository.activity.timeline

import com.fathersprophets.backend.database.dao.activity.timeline.TimelineDao
import com.fathersprophets.backend.models.ApiResponse
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

    override fun createTimeline(request: CreateTimelineRequest, lang: String): ApiResponse<Int> {
        val id = dao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("timeline_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("timeline_created_successfully", lang)
        )
    }

    override fun updateTimeline(id: Int, request: UpdateTimelineRequest, lang: String): ApiResponse<Nothing> {

        val updated = dao.update(request.convertToDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("timeline_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("timeline_updated_successfully", lang)
        )
    }

    override fun deleteTimeline(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(id)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("timeline_deleted_successfully", lang)
        )
    }
}