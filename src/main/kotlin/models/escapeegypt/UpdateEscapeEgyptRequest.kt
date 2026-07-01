package com.fathersprophets.backend.models.escapeegypt

import com.fathersprophets.backend.database.tables.EscapeEgyptType
import com.fathersprophets.backend.models.dto.EscapeEgyptDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateEscapeEgyptRequest(
    val title: String,
    val type: String
) {
    fun convertToDto(id: Int) = EscapeEgyptDto(
        id = id,
        title = title,
        type = EscapeEgyptType.valueOf(type)
    )
}