package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.session.SessionResponse

data class SessionDto(
    val id: Int,
    val dateTime: String,
    val createdAt: String,
){
    fun convertToSessionResponse(): SessionResponse {
        return SessionResponse(
            id = this.id,
            dateTime = this.dateTime,
            createdAt = this.createdAt
        )
    }
}