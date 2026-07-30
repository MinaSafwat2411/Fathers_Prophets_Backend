package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.attendance.AttendanceResponse


data class AttendanceDto(
    val id: Int,
    val userId: Int,
    val sessionId: Int,
    val name: String,
    val attended: Boolean,
    val broughtBible: Boolean,
    val shmas: Boolean,
    val odas: Boolean,
    val tnawl: Boolean,
    val classId: Int
){
    fun convertAttendanceResponse() = AttendanceResponse(
        id = id,
        userId = userId,
        sessionId = sessionId,
        name = name,
        attended = attended,
        broughtBible = broughtBible,
        shmas = shmas,
        odas = odas,
        tnawl = tnawl,
        classId = classId
    )
}
