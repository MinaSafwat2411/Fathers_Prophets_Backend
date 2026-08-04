package com.fathersprophets.backend.modules.sessionattendance

import kotlinx.serialization.Serializable

@Serializable
data class SessionAttendanceDto(
    val id: Int,
    val userId: Int,
    val sessionId: Int,
    val attended: Boolean,
    val broughtBible: Boolean,
    val shmas: Boolean,
    val odas: Boolean,
    val tnawl: Boolean,
    val classId: Int
)

@Serializable
data class SessionAttendanceCreateDto(
    val userId: Int,
    val sessionId: Int,
    val attended: Boolean = false,
    val broughtBible: Boolean = false,
    val shmas: Boolean = false,
    val odas: Boolean = false,
    val tnawl: Boolean = false,
    val classId: Int
)

@Serializable
data class SessionAttendanceUpdateDto(
    val userId: Int? = null,
    val sessionId: Int? = null,
    val attended: Boolean? = null,
    val broughtBible: Boolean? = null,
    val shmas: Boolean? = null,
    val odas: Boolean? = null,
    val tnawl: Boolean? = null,
    val classId: Int? = null
)