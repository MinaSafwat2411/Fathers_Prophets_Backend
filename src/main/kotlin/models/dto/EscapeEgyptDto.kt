package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.EscapeEgyptType
import com.fathersprophets.backend.models.escapeegypt.EscapeEgyptResponse

data class EscapeEgyptDto(
    val id: Int,
    val title: String,
    val type: EscapeEgyptType
) {
    fun convertToResponse() = EscapeEgyptResponse(
        id = id,
        title = title,
        type = type.name
    )
}