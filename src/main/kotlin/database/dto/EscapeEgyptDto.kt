package com.fathersprophets.backend.database.dto

import com.fathersprophets.backend.database.enums.EscapeEgyptType


data class EscapeEgyptDto(
    val id: Int,
    val title: String,
    val type: EscapeEgyptType
)