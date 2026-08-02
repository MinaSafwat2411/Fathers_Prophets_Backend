package com.fathersprophets.backend.database.dto

data class SessionDto(
    val id: Int,
    val familyId : Int,
    val dateTime: String,
    val createdAt: String,
)