package com.fathersprophets.backend.models.escapeegypt

import kotlinx.serialization.Serializable

@Serializable
data class EscapeEgyptResponse(
    val id: Int,
    val title: String,
    val type: String
)