package com.fathersprophets.backend.database.dto.session

data class AttendanceDto(
    val id: Int,
    val userId: Int,
    val sessionId: Int,
    val classId: Int,
    val attended: Boolean?,
    val broughtBible: Boolean?,
    val shmas: Boolean?,
    val odas: Boolean?,
    val tnawl: Boolean?,
)
