package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.event.EventResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventBroadcaster {
    private val _eventFlow = MutableSharedFlow<ApiResponse<List<EventResponse>>>()
    val eventFlow = _eventFlow.asSharedFlow()

    suspend fun broadcastEvents(events: ApiResponse<List<EventResponse>>) {
        _eventFlow.emit(events)
    }
}
