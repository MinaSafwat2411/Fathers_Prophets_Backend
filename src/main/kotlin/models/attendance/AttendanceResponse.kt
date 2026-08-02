package com.fathersprophets.backend.models.attendance

import com.fathersprophets.backend.database.dto.AttendanceDto
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceResponse(
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
    fun toAttendanceDto()= AttendanceDto(
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
