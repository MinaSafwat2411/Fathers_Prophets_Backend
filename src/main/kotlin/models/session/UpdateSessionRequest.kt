package com.fathersprophets.backend.models.session

import com.fathersprophets.backend.models.dto.SessionDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateSessionRequest(
    val dateTime : String? =null
){
    fun toSessionDto(id : Int) = SessionDto(
        dateTime = dateTime ?: "",
        id = id,
        createdAt = ""
    )
}
