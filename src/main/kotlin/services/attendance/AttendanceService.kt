package com.fathersprophets.backend.services.attendance

import com.fathersprophets.backend.database.repository.attendance.IAttendanceRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.AttendanceResponse
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class AttendanceService(private val attendanceRepository: IAttendanceRepository) : IAttendanceService {
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
        return attendanceRepository.addAttendance(attendance, lang)
    }

    override fun updateAttendance(
        attendanceId: Int?,
        updateAttendance: UpdateAttendanceRequest,
        lang: String
    ): ApiResponse<AttendanceResponse> {
        if (attendanceId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }

        return attendanceRepository.updateAttendance(attendanceId, updateAttendance, lang)
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
