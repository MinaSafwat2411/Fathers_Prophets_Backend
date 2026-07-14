package com.fathersprophets.backend.services.activity.timeline.timelineanswer

import com.fathersprophets.backend.database.repository.activity.timeline.timelineanswer.ITimelineAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.timelineanswer.CreateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.TimelineAnswerResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class TimelineAnswerService(
    private val repository: ITimelineAnswerRepository
) : ITimelineAnswerService {

    override fun getAllAnswers(lang: String): ApiResponse<List<TimelineAnswerResponse>> {
        return repository.getAllAnswers(lang)
    }


    override fun getAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<TimelineAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return repository.getAnswersByUserId(userId, lang)
    }

    override fun createAnswer(request: CreateTimelineAnswerRequest, lang: String): ApiResponse<TimelineAnswerResponse> {
        validateRequired(
            request.timelineId to "timelineId",
            request.userId to "userId",
            lang = lang
        )
        if (request.order.isEmpty()) throw IllegalArgumentException(Localization.get("timeline_answer_order_required", lang))
        return repository.createAnswer(request, lang)
    }

    override fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("timeline_answer_id_required", lang))
        return repository.deleteAnswer(id, lang)
    }
}