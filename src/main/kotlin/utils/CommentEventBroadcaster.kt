package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.comments.CommentResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object CommentEventBroadcaster {
    private val _commentEvents = MutableSharedFlow<Pair<Int, ApiResponse<CommentResponse>>>()
    val commentEvents: SharedFlow<Pair<Int, ApiResponse<CommentResponse>>> = _commentEvents

    suspend fun broadcastComment(userId: Int, response: ApiResponse<CommentResponse>) {
        _commentEvents.emit(userId to response)
    }
}

