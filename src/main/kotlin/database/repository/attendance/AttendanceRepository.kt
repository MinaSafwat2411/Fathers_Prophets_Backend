package com.fathersprophets.backend.database.repository.attendance

import com.fathersprophets.backend.database.dao.attendance.AttendanceDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.AttendanceResponse
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.models.dto.AttendanceDto
import com.fathersprophets.backend.utils.Localization

class AttendanceRepository(
    private val attendanceDao: AttendanceDao
) : IAttendanceRepository {
    override fun addAttendance(
        attendance: AddAttendanceRequest,
        lang: String
    ): ApiResponse<AttendanceResponse> {
        val id = attendanceDao.addAttendance(attendance.toAttendanceDto())
        val created = attendanceDao.getAttendanceById(idToAttendanceDto(id))
            ?: throw IllegalStateException("attendance_create_failed")

        return ApiResponse(
            success = true,
            data = created.convertAttendanceResponse(),
            message = Localization.get("attendance_create_success", lang)
        )
    }

    override fun updateAttendance(
        attendanceId: Int,
        updateAttendance: UpdateAttendanceRequest,
        lang: String
    ): ApiResponse<AttendanceResponse> {
        attendanceDao.updateAttendance(updateAttendance.toAttendanceDto(attendanceId))

        return ApiResponse(
            success = true,
            data = updateAttendance.toAttendanceDto(attendanceId).convertAttendanceResponse(),
            message = Localization.get("attendance_update_success", lang)
        )
    }

    override fun deleteAttendance(
        attendanceId: Int,
        lang: String
    ): ApiResponse<Nothing> {
        attendanceDao.deleteAttendance(idToAttendanceDto(attendanceId))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("attendance_delete_success", lang)
        )
    }

    override fun getAttendanceByUserId(
        userId: Int,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        val list = attendanceDao.getAllAttendanceByUserId(idToAttendanceDto(userId))
        return ApiResponse(
            success = true,
            data = list.map { it.convertAttendanceResponse() },
            message = Localization.get("attendance_retrieved_success", lang)
        )
    }

    override fun getAttendanceBySessionId(
        sessionId: Int,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        val list = attendanceDao.getAllAttendanceBySessionId(idToAttendanceDto(sessionId))
        return ApiResponse(
            success = true,
            data = list.map { it.convertAttendanceResponse() },
            message = Localization.get("attendance_retrieved_success", lang)
        )
    }

    override fun getAllAttendance(lang: String): ApiResponse<List<AttendanceResponse>> {
        val list = attendanceDao.getAllAttendance()
        return ApiResponse(
            success = true,
            data = list.map { it.convertAttendanceResponse() },
            message = Localization.get("attendance_retrieved_success", lang)
        )
    }

    override fun getAttendanceByClassId(classId: Int, lang: String): ApiResponse<List<AttendanceResponse>> {
        val list = attendanceDao.getAllAttendanceByClassId(idToAttendanceDto(classId))

        return ApiResponse(
            success = true,
            data = list.map { it.convertAttendanceResponse() },
            message = Localization.get("attendance_retrieved_success", lang)
        )
    }

    private fun idToAttendanceDto(id: Int) = AttendanceDto(
        id = id,
        userId = id,
        sessionId = id,
        name = "",
        attended = false,
        broughtBible = false,
        shmas = false,
        odas = false,
        tnawl = false,
        classId = id
    )
}
