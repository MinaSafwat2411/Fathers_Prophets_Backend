package com.fathersprophets.backend.models.attendance

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAttendanceRequest(
    val attended: Boolean? = null,
    val broughtBible: Boolean? = null,
    val shmas: Boolean? = null,
    val odas: Boolean? = null,
    val tnawl: Boolean? = null,
) {
    fun toAttendanceDto(id: Int) = com.fathersprophets.backend.models.dto.AttendanceDto(
        id = id,
        userId = 0,
        sessionId = 0,
        name = "",
        attended = attended ?: false,
        broughtBible = broughtBible ?: false,
        shmas = shmas ?: false,
        odas = odas ?: false,
        tnawl = tnawl ?: false,
        classId =  0
    )
}
