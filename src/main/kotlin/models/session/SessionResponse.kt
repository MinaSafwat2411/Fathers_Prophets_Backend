package com.fathersprophets.backend.models.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(
    val id: Int,
    val dateTime: String,
    val createdAt: String,
)
