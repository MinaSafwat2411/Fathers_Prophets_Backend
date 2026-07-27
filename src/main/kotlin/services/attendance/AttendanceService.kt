package com.fathersprophets.backend.services.attendance

import com.fathersprophets.backend.database.repository.attendance.attendance.IAttendanceRepository
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AddAttendanceBulkRequest
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

        val result = attendanceRepository.addAttendance(attendance, lang)
        if (result.success) broadcastSession(result.data?.sessionId, lang)
        return result
    }

    override fun addAttendanceBulk(
        request: AddAttendanceBulkRequest,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        val records = request.resolvedRecords()

        if (records.isEmpty()) {
            throw BadRequestException(Localization.get("attendance_records_required", lang))
        }

        records.forEachIndexed { index, record ->
            validateRequired(
                record.userId to "records[$index].user_id",
                record.sessionId to "records[$index].session_id",
                record.classId to "records[$index].class_id",
                lang = lang
            )
        }

        // One user cannot be marked twice in the same session, so reject it before the batch hits the unique index.
        if (records.distinctBy { it.userId to it.sessionId }.size != records.size) {
            throw BadRequestException(Localization.get("attendance_duplicate_records", lang))
        }

        val result = attendanceRepository.addAttendanceBulk(records, lang)
        if (result.success) {
            result.data?.map { it.sessionId }?.distinct()?.forEach { broadcastSession(it, lang) }
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
        if (result.success) broadcastSession(result.data?.sessionId, lang)
        return result
    }

    override fun deleteAttendance(
        attendanceId: Int?,
        lang: String
    ): ApiResponse<Nothing> {
        if (attendanceId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }

        // The record is gone once it is deleted, so read its session first.
        val sessionId = attendanceRepository.findAttendanceById(attendanceId)?.sessionId

        val result = attendanceRepository.deleteAttendance(attendanceId, lang)
        if (result.success) broadcastSession(sessionId, lang)
        return result
    }

    /** Pushes the whole session to everyone watching it after a change. */
    private fun broadcastSession(sessionId: Int?, lang: String) {
        if (sessionId == null) return

        val sessionAttendance = attendanceRepository.getAttendanceBySessionId(sessionId, lang)
        scope.launch {
            try {
                AttendanceEventBroadcaster.broadcastAttendance(sessionId, sessionAttendance)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

    override fun getAttendanceByClassIdAndSessionId(
        userId: Int?,
        sessionId: Int?,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        if (userId == null || sessionId == null) {
            throw IllegalArgumentException(Localization.get("invalid_id", lang))
        }
        return attendanceRepository.getAttendanceByClassIdAndSessionId(userId, sessionId, lang)
    }

    override fun getAllAttendance(lang: String): ApiResponse<List<AttendanceResponse>> {
        return attendanceRepository.getAllAttendance(lang)
    }
}
