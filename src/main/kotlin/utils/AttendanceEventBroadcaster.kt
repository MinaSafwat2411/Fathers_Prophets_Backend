package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AttendanceResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object AttendanceEventBroadcaster {
    private val _attendanceEvents = MutableSharedFlow<Pair<Int, ApiResponse<List<AttendanceResponse>>>>()
    val attendanceEvents: SharedFlow<Pair<Int, ApiResponse<List<AttendanceResponse>>>> = _attendanceEvents

    suspend fun broadcastAttendance(sessionId: Int, response: ApiResponse<List<AttendanceResponse>>) {
        _attendanceEvents.emit(sessionId to response)
    }
}
