package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.eventmember.EventMemberResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventMemberBroadcaster {
    private val _eventMemberFlow = MutableSharedFlow<Pair<Int, ApiResponse<List<EventMemberResponse>>>>()
    val eventMemberFlow = _eventMemberFlow.asSharedFlow()

    suspend fun broadcastEventMembers(eventId: Int, members: ApiResponse<List<EventMemberResponse>>) {
        _eventMemberFlow.emit(eventId to members)
    }
}
