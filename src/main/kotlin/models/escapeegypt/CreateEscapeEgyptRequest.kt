package com.fathersprophets.backend.models.escapeegypt

import com.fathersprophets.backend.database.tables.EscapeEgyptType
import com.fathersprophets.backend.models.dto.EscapeEgyptDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateEscapeEgyptRequest(
    val title: String,
    val type: String
) {
    fun convertToDto() = EscapeEgyptDto(
        id = 0,
        title = title,
        type = EscapeEgyptType.valueOf(type)
    )
}