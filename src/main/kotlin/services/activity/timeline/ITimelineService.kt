package com.fathersprophets.backend.services.activity.timeline

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.timeline.CreateTimelineRequest
import com.fathersprophets.backend.models.timeline.TimelineResponse
import com.fathersprophets.backend.models.timeline.UpdateTimelineRequest

interface ITimelineService {
    fun getAllTimelines(lang: String): ApiResponse<List<TimelineResponse>>
    fun getTimelineById(id: Int?, lang: String): ApiResponse<TimelineResponse>
    fun createTimeline(request: CreateTimelineRequest, lang: String): ApiResponse<Int>
    fun updateTimeline(id: Int?, request: UpdateTimelineRequest, lang: String): ApiResponse<Nothing>
    fun deleteTimeline(id: Int?, lang: String): ApiResponse<Nothing>
}