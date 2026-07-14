package com.fathersprophets.backend.services.activity.timeline.timelineanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.timelineanswer.CreateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.TimelineAnswerResponse
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.UpdateTimelineAnswerStatusRequest

interface ITimelineAnswerService {
    fun getAllAnswers(lang: String): ApiResponse<List<TimelineAnswerResponse>>
    fun getAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<TimelineAnswerResponse>>
    fun createAnswer(request: CreateTimelineAnswerRequest, lang: String): ApiResponse<TimelineAnswerResponse>
    fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing>
}