package com.fathersprophets.backend.services.attendance

import com.fathersprophets.backend.database.repository.attendance.attendance.IAttendanceRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.AttendanceResponse
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.utils.AttendanceEventBroadcaster
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttendanceService(private val attendanceRepository: IAttendanceRepository) : IAttendanceService {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    
    override fun addAttendance(
        attendance: AddAttendanceRequest,
        lang: String
    ): ApiResponse<AttendanceResponse> {
        validateRequired(
            attendance.userId to "user_id",
            attendance.sessionId to "session_id",
            attendance.classId to "attendance_status",
            lang = lang
        )
        if(attendance.sessionId == null) {
            throw IllegalArgumentException(Localization.get("session_id_required", lang))
        }
        val result = attendanceRepository.addAttendance(attendance, lang)
        if (result.success) {
            val sessionAttendance = attendanceRepository.getAttendanceBySessionId(attendance.sessionId, lang)
            scope.launch {
                AttendanceEventBroadcaster.broadcastAttendance(attendance.sessionId, sessionAttendance)
            }
        }
        return result
    }

    override fun updateAttendance(
        attendanceId: Int?,
        updateAttendance: UpdateAttendanceRequest,
        lang: String
    ): ApiResponse<AttendanceResponse> {
        if (attendanceId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }

        val result = attendanceRepository.updateAttendance(attendanceId, updateAttendance, lang)
        if (result.success && result.data != null) {
            val sessionId = result.data.sessionId
            val sessionAttendance = attendanceRepository.getAttendanceBySessionId(sessionId, lang)
            scope.launch {
                AttendanceEventBroadcaster.broadcastAttendance(sessionId, sessionAttendance)
            }
        }
        return result
    }

    override fun deleteAttendance(
        attendanceId: Int?,
        lang: String
    ): ApiResponse<Nothing> {
        if (attendanceId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        return attendanceRepository.deleteAttendance(attendanceId, lang)
    }

    override fun getAttendanceByUserId(
        userId: Int?,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        if (userId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        return attendanceRepository.getAttendanceByUserId(userId, lang)
    }

    override fun getAttendanceBySessionId(
        sessionId: Int?,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        if (sessionId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        return attendanceRepository.getAttendanceBySessionId(sessionId, lang)
    }

    override fun getAllAttendance(lang: String): ApiResponse<List<AttendanceResponse>> {
        return attendanceRepository.getAllAttendance(lang)
    }

    override fun getAttendanceByClassId(
        classId: Int?,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        if (classId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        return attendanceRepository.getAttendanceByClassId(classId, lang)
    }
}
