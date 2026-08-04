package com.fathersprophets.backend.modules.escapeegypt

import com.fathersprophets.backend.database.enums.EscapeEgyptType
import kotlinx.serialization.Serializable

@Serializable
data class EscapeEgyptDto(
    val id: Int,
    val title: String,
    val type: EscapeEgyptType
)

@Serializable
data class EscapeEgyptCreateDto(
    val title: String,
    val type: EscapeEgyptType
)

@Serializable
data class EscapeEgyptUpdateDto(
    val title: String? = null,
    val type: EscapeEgyptType? = null
)