package com.fathersprophets.backend.database.repository.attendance.attendance

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.AttendanceResponse
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest

interface IAttendanceRepository {
    fun addAttendance(attendance: AddAttendanceRequest, lang: String): ApiResponse<AttendanceResponse>
    fun updateAttendance(attendanceId: Int, updateAttendance: UpdateAttendanceRequest, lang: String): ApiResponse<AttendanceResponse>
    fun deleteAttendance(attendanceId: Int, lang: String): ApiResponse<Nothing>
    fun getAttendanceByUserId(userId: Int, lang: String): ApiResponse<List<AttendanceResponse>>
    fun getAttendanceBySessionId(sessionId: Int, lang: String): ApiResponse<List<AttendanceResponse>>
    fun getAttendanceByClassIdAndSessionId(userId: Int, sessionId: Int, lang: String): ApiResponse<List<AttendanceResponse>>
    fun getAllAttendance(lang: String): ApiResponse<List<AttendanceResponse>>
    fun getAttendanceByClassId(classId: Int, lang: String): ApiResponse<List<AttendanceResponse>>

}