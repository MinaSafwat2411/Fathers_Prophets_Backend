package com.fathersprophets.backend.database.repository.attendance.attendance

import com.fathersprophets.backend.database.dao.attendance.AttendanceDao
import com.fathersprophets.backend.database.dao.classes.ClassDao
import com.fathersprophets.backend.database.dao.classes.ClassMemberDao
import com.fathersprophets.backend.database.dao.users.UserDao
import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.AttendanceResponse
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.utils.Localization

class AttendanceRepository(
    private val attendanceDao: AttendanceDao,
    private val classMemberDao: ClassMemberDao
) : IAttendanceRepository {
    override fun addAttendance(
        attendance: AddAttendanceRequest,
        lang: String
    ): ApiResponse<AttendanceResponse> {
        val create = attendanceDao.addAttendance(attendance.toAttendanceDto())
            ?: throw IllegalArgumentException(Localization.get("attendance_create_failed", lang))

        return ApiResponse(
            success = true,
            data = create.convertAttendanceResponse(),
            message = Localization.get("attendance_create_success", lang)
        )
    }

    override fun updateAttendance(
        attendanceId: Int,
        updateAttendance: UpdateAttendanceRequest,
        lang: String
    ): ApiResponse<AttendanceResponse> {

        val updated = attendanceDao.updateAttendance(updateAttendance.toAttendanceDto(attendanceId))
            ?:throw  IllegalArgumentException(Localization.get("attendance_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertAttendanceResponse(),
            message = Localization.get("attendance_update_success", lang)
        )
    }

    override fun deleteAttendance(
        attendanceId: Int,
        lang: String
    ): ApiResponse<Nothing> {

        val  deleted = attendanceDao.deleteAttendance(attendanceId)

        if(!deleted) throw  IllegalArgumentException(Localization.get("attendance_delete_failed", lang))


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
        val list = attendanceDao.getAllAttendanceByUserId(userId)
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
        val list = attendanceDao.getAllAttendanceBySessionId(sessionId)
        return ApiResponse(
            success = true,
            data = list.map { it.convertAttendanceResponse() },
            message = Localization.get("attendance_retrieved_success", lang)
        )
    }

    override fun getAttendanceByClassIdAndSessionId(
        userId: Int,
        sessionId: Int,
        lang: String
    ): ApiResponse<List<AttendanceResponse>> {
        val classMember = classMemberDao.findByUserId(userId) ?: throw ForbiddenException(Localization.get("user_not_found", lang))

        val classId = classMember.classId

        val list = attendanceDao.getAllAttendanceByClassIdAndSessionId(classId, sessionId)

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
}
