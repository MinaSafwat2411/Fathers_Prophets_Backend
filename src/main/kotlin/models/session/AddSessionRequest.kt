package com.fathersprophets.backend.models.session

import com.fathersprophets.backend.database.dto.SessionDto
import kotlinx.serialization.Serializable

@Serializable
data class AddSessionRequest(
    val dateTime : String? = null
){
    fun toSessionDto() = SessionDto(
        id = 0,
        dateTime = this.dateTime ?: "",
        createdAt = ""
    )
}
