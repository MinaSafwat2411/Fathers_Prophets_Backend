package com.fathersprophets.backend.database.dto

data class AttendanceDto(
    val id: Int,
    val userId: Int,
    val sessionId: Int,
    val attended: Boolean?,
    val broughtBible: Boolean?,
    val shmas: Boolean?,
    val odas: Boolean?,
    val tnawl: Boolean?,
    val classId: Int
)
