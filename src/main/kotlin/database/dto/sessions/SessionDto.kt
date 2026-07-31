package com.fathersprophets.backend.database.dto.sessions

data class SessionDto(
    val id: Int,
    val familyId : Int,
    val dateTime: String,
    val createdAt: String,
)