package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.session.SessionResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object SessionEventBroadcaster {
    private val _sessionEvents = MutableSharedFlow<ApiResponse<List<SessionResponse>>>()
    val sessionEvents: SharedFlow<ApiResponse<List<SessionResponse>>> = _sessionEvents

    suspend fun broadcastSessions(response: ApiResponse<List<SessionResponse>>) {
        _sessionEvents.emit(response)
    }
}