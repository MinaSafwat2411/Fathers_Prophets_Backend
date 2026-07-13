package com.fathersprophets.backend.services.activity.timeline

import com.fathersprophets.backend.database.repository.activity.timeline.ITimelineRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.timeline.CreateTimelineRequest
import com.fathersprophets.backend.models.timeline.TimelineResponse
import com.fathersprophets.backend.models.timeline.UpdateTimelineRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class TimelineService(
    private val repository: ITimelineRepository
) : ITimelineService {

    override fun getAllTimelines(lang: String): ApiResponse<List<TimelineResponse>> {
        return repository.getAllTimelines(lang)
    }

    override fun getTimelineById(id: Int?, lang: String): ApiResponse<TimelineResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("timeline_id_required", lang))
        return repository.getTimelineById(id, lang)
    }

    override fun createTimeline(request: CreateTimelineRequest, lang: String): ApiResponse<Int> {
        validateRequired(
            request.event1 to "event1",
            request.event2 to "event2",
            request.event3 to "event3",
            request.event4 to "event4",
            lang = lang
        )
        if (request.correctOrder.size != 4) throw IllegalArgumentException(Localization.get("timeline_correct_order_invalid", lang))
        return repository.createTimeline(request, lang)
    }

    override fun updateTimeline(id: Int?, request: UpdateTimelineRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("timeline_id_required", lang))
        validateRequired(
            request.event1 to "event1",
            request.event2 to "event2",
            request.event3 to "event3",
            request.event4 to "event4",
            lang = lang
        )
        if (request.correctOrder.size != 4) throw IllegalArgumentException(Localization.get("timeline_correct_order_invalid", lang))
        return repository.updateTimeline(id, request, lang)
    }

    override fun deleteTimeline(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("timeline_id_required", lang))
        return repository.deleteTimeline(id, lang)
    }
}