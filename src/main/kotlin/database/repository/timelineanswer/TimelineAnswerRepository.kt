package com.fathersprophets.backend.database.repository.timelineanswer

import com.fathersprophets.backend.database.dao.activity.timeline.TimelineAnswerDao
import com.fathersprophets.backend.database.dao.activity.timeline.TimelineDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.TimelineAnswerDto
import com.fathersprophets.backend.models.timelineanswer.CreateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.TimelineAnswerResponse
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerStatusRequest
import com.fathersprophets.backend.utils.Localization

class TimelineAnswerRepository(
    private val answerDao: TimelineAnswerDao,
    private val timelineDao: TimelineDao
) : ITimelineAnswerRepository {

    override fun getAllAnswers(lang: String): ApiResponse<List<TimelineAnswerResponse>> {
        val answers = answerDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("timeline_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswerById(id: Int, lang: String): ApiResponse<TimelineAnswerResponse> {
        val answer = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToResponse(),
            message = Localization.get("timeline_answer_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByTimelineId(timelineId: Int, lang: String): ApiResponse<List<TimelineAnswerResponse>> {
        val answers = answerDao.findByTimelineId(timelineId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("timeline_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<TimelineAnswerResponse>> {
        val answers = answerDao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("timeline_answers_retrieved_successfully", lang)
        )
    }

    override fun createAnswer(request: CreateTimelineAnswerRequest, lang: String): ApiResponse<TimelineAnswerResponse> {
        val existing = answerDao.findByTimelineIdAndUserId(request.timelineId, request.userId)
        if (existing != null) throw IllegalStateException(Localization.get("timeline_answer_already_exists", lang))

        val timeline = timelineDao.findById(request.timelineId)
            ?: throw IllegalArgumentException(Localization.get("timeline_not_found", lang))

        val status = gradeOrder(request.order, timeline.correctOrder)

        val id = answerDao.create(
            TimelineAnswerDto(
                id = 0,
                timelineId = request.timelineId,
                userId = request.userId,
                status = status
            )
        )
        val created = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("timeline_answer_created_successfully", lang)
        )
    }

    override fun updateAnswer(id: Int, request: UpdateTimelineAnswerRequest, lang: String): ApiResponse<TimelineAnswerResponse> {
        val timeline = timelineDao.findById(request.timelineId)
            ?: throw IllegalArgumentException(Localization.get("timeline_not_found", lang))

        val status = gradeOrder(request.order, timeline.correctOrder)

        answerDao.update(
            TimelineAnswerDto(
                id = id,
                timelineId = request.timelineId,
                userId = request.userId,
                status = status
            )
        )
        val updated = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("timeline_answer_updated_successfully", lang)
        )
    }

    override fun updateAnswerStatus(id: Int, request: UpdateTimelineAnswerStatusRequest, lang: String): ApiResponse<TimelineAnswerResponse> {
        answerDao.updateStatus(idToDto(id, AnswerStatus.valueOf(request.status)))
        val updated = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("timeline_answer_status_updated_successfully", lang)
        )
    }

    override fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        answerDao.delete(idToDto(id, AnswerStatus.IS_FALSE))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("timeline_answer_deleted_successfully", lang)
        )
    }

    private fun gradeOrder(order: List<Int>, correctOrder: List<Int>) =
        if (order == correctOrder) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE

    private fun idToDto(id: Int, status: AnswerStatus) = TimelineAnswerDto(
        id = id,
        timelineId = 0,
        userId = 0,
        status = status
    )
}