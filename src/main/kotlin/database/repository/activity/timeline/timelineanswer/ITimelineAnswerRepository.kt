package com.fathersprophets.backend.database.repository.activity.timeline.timelineanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.timelineanswer.CreateTimelineAnswerRequest
import com.fathersprophets.backend.models.timelineanswer.TimelineAnswerResponse

interface ITimelineAnswerRepository {
    fun getAllAnswers(lang: String): ApiResponse<List<TimelineAnswerResponse>>
    fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<TimelineAnswerResponse>>
    fun createAnswer(request: CreateTimelineAnswerRequest, lang: String): ApiResponse<TimelineAnswerResponse>
    fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing>
}