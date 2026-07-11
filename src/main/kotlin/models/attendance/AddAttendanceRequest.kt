package com.fathersprophets.backend.models.attendance

import com.fathersprophets.backend.models.dto.AttendanceDto
import kotlinx.serialization.Serializable

@Serializable
data class AddAttendanceRequest(
    val userId: Int? = null,
    val sessionId: Int? = null,
    val name: String? = null,
    val attended: Boolean? = null,
    val broughtBible: Boolean? = null,
    val shmas: Boolean? = null,
    val odas: Boolean? = null,
    val tnawl: Boolean? = null,
    val classId: Int? = null
){
    fun toAttendanceDto(int: Int = 0) = AttendanceDto(
        id = int,
        userId = userId ?: 0,
        sessionId = sessionId ?: 0,
        name = name ?: "",
        attended = attended ?: false,
        broughtBible = broughtBible ?: false,
        shmas = shmas ?: false,
        odas = odas ?: false,
        tnawl = tnawl ?: false,
        classId = classId ?: 0
    )
}
