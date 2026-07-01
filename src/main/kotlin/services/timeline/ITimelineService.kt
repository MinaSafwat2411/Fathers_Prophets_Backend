package com.fathersprophets.backend.services.timeline

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.timeline.CreateTimelineRequest
import com.fathersprophets.backend.models.timeline.TimelineResponse
import com.fathersprophets.backend.models.timeline.UpdateTimelineRequest

interface ITimelineService {
    fun getAllTimelines(lang: String): ApiResponse<List<TimelineResponse>>
    fun getTimelineById(id: Int?, lang: String): ApiResponse<TimelineResponse>
    fun createTimeline(request: CreateTimelineRequest, lang: String): ApiResponse<TimelineResponse>
    fun updateTimeline(id: Int?, request: UpdateTimelineRequest, lang: String): ApiResponse<TimelineResponse>
    fun deleteTimeline(id: Int?, lang: String): ApiResponse<Nothing>
}