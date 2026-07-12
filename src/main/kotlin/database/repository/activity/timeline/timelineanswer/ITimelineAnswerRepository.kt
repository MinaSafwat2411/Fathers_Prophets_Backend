package com.fathersprophets.backend.database.repository.activity.timeline.timelineanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.timelineanswer.CreateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.TimelineAnswerResponse
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerStatusRequest

interface ITimelineAnswerRepository {
    fun getAllAnswers(lang: String): ApiResponse<List<TimelineAnswerResponse>>
    fun getAnswerById(id: Int, lang: String): ApiResponse<TimelineAnswerResponse>
    fun getAnswersByTimelineId(timelineId: Int, lang: String): ApiResponse<List<TimelineAnswerResponse>>
    fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<TimelineAnswerResponse>>
    fun createAnswer(request: CreateTimelineAnswerRequest, lang: String): ApiResponse<Int>
    fun updateAnswer(id: Int, request: UpdateTimelineAnswerRequest, lang: String): ApiResponse<Nothing>
    fun updateAnswerStatus(id: Int, request: UpdateTimelineAnswerStatusRequest, lang: String): ApiResponse<Nothing>
    fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing>
}